; ThreadWorker.native.ll
; 
; Author:
; Claude Abounegm

;-------------
; Definitions
;-------------
; Primitives
%boolean = type i1
%byte = type i8
%ubyte = type i8
%short = type i16
%ushort = type i16
%int = type i32
%uint = type i32
%code = type i32
%long = type i64
%ulong = type i64
%float = type float
%double = type double
%void = type i8

; Object
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type opaque

; ThreadWorker
%shadow.standard..ThreadWorker_methods = type opaque
%shadow.standard..ThreadWorker = type opaque

; typedef uintptr_t pthread_t;
%struct.pthread_t = type %uint

; struct pthread_attr_t { unsigned p_state; void* stack; size_t s_size; struct sched_param param; };
%struct.pthread_attr_t = type { %int, %void*, %int, %struct.sched_param }

; struct sched_param { int sched_priority; };
%struct.sched_param = type { %int }

;---------
; Globals
;---------
; used to store the current instance of the thread; Thread->current.
@shadow.standard..ThreadWorker_currentThread = thread_local global %shadow.standard..ThreadWorker* null
@shadow.standard..ThreadWorker_mainThread = global %shadow.standard..ThreadWorker* null
@nextThreadId = private global %int 0

;---------------------
; Method Declarations
;---------------------
; int pthread_create(pthread_t*, pthread_attr_t*, void* (*start_routine)(void*), void*);
declare %int @pthread_create(%struct.pthread_t*, %struct.pthread_attr_t*, %void* (%void*)*, %void*)

; int pthread_join(pthread_t, void**);
declare %int @pthread_join(%struct.pthread_t, %void**)

; void* calloc(int num, int size);
declare noalias %void* @calloc(%int, %int) nounwind

; void free(void* ptr);
declare void @free(%void*) nounwind

; runnerNative() => ();
declare void @shadow.standard..ThreadWorker_MrunnerNative(%shadow.standard..ThreadWorker*)

; createNative() => (ThreadWorker);
declare %shadow.standard..ThreadWorker* @shadow.standard..ThreadWorker_McreateNative(%shadow.standard..ThreadWorker*)

; unlockMutexNative() => ();
declare void @shadow.standard..ThreadWorker_MunlockMutexNative(%shadow.standard..ThreadWorker*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; get staticNextId() => (int); (ThreadSafe)
define %int @shadow.standard..ThreadWorker_MstaticNextId(%shadow.standard..ThreadWorker*) {
entry:
	%currentId = atomicrmw add %int* @nextThreadId, %int 1 seq_cst
	ret %int %currentId
}

; spawnThread(immutable Object handle) => (int);
define %int @shadow.standard..ThreadWorker_MspawnThread_shadow.standard..Object(%shadow.standard..ThreadWorker*, %shadow.standard..Object*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; get the handle
	%handle.addr = bitcast %shadow.standard..Object* %1 to %struct.pthread_t*
	
	; cast Thread* to void*
	%this.void = bitcast %shadow.standard..ThreadWorker* %this to %void*

	; create the thread using pthread_create()
	%call = call %int @pthread_create(%struct.pthread_t* %handle.addr, %struct.pthread_attr_t* null, %void*(%void*)* @thread_start, %void* %this.void)

	ret %int %call
}

; joinThread(immutable Object ptr) => (int);
define %int @shadow.standard..ThreadWorker_MjoinThread_shadow.standard..Object(%shadow.standard..ThreadWorker*, %shadow.standard..Object*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; load handle
	%handle.addr = bitcast %shadow.standard..Object* %1 to %struct.pthread_t*
	%handle = load %struct.pthread_t, %struct.pthread_t* %handle.addr
	
	; we unlock the mutex before joining
	call void @shadow.standard..ThreadWorker_MunlockMutexNative(%shadow.standard..ThreadWorker* %this)
	
	; join thread
	%call = call %int @pthread_join(%struct.pthread_t %handle, %void** null)

	ret %int %call
}

; get handleSize() => (int);
define %int @shadow.standard..ThreadWorker_MhandleSize(%shadow.standard..ThreadWorker*) {
entry:
	%sizeOfPthread = ptrtoint %struct.pthread_t* getelementptr (%struct.pthread_t, %struct.pthread_t* null, i32 1) to i32
	
	ret %int %sizeOfPthread
}

;---------------------------
; Custom Method Definitions
;---------------------------
; the function ran from the newly spawned thread
define %void* @thread_start(%void*) {
entry:
	%currentThread.addr = bitcast %void* %0 to %shadow.standard..ThreadWorker*

	; we need to set the reference of the current thread in this function as it is executed from the newly created thread
	; and will cause the TLS to correctly store the reference of this thread.
	store %shadow.standard..ThreadWorker* %currentThread.addr, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread

	; we let Shadow take care of running the actual desired operation
	call void @shadow.standard..ThreadWorker_MrunnerNative(%shadow.standard..ThreadWorker* %currentThread.addr)

	ret %void* null
}

; initializes the main thread and set the currentThread and mainThread to that instance
define void @shadow.standard..ThreadWorker_MinitMainThread() {
entry:
	; we initialize the dummy ThreadWorker for the main thread
	%mainThread = call %shadow.standard..ThreadWorker* @shadow.standard..ThreadWorker_McreateNative(%shadow.standard..ThreadWorker* null)
	
	; each thread needs to be able to get a reference to its own ThreadWorker, so we set its instance to the currentThread TLS.
	store %shadow.standard..ThreadWorker* %mainThread, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread
	
	; each thread should also be able to reference the main thread from anywhere, as it is the root of all threads.
	store %shadow.standard..ThreadWorker* %mainThread, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_mainThread
	
	ret void
}
