; Mutex.native.ll
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
%shadow.standard..Object = type opaque

; Thread
%shadow.standard..Thread = type opaque

; Pointer
%shadow.natives..Pointer = type opaque

; Mutex
%shadow.natives..Mutex = type opaque

; Mutex.Owner
%shadow.natives..Mutex.Owner = type opaque

; pthread_mutex_t
%struct.pthread_mutex_t = type i8*

;---------------------
; Method Declarations
;---------------------
; int pthread_mutex_init(pthread_mutex_t* mutex, pthread_mutexattr_t* attr);
declare %int @pthread_mutex_init(%struct.pthread_mutex_t*, %int*)
; int pthread_mutex_destroy(pthread_mutex_t *mutex);
declare %int @pthread_mutex_destroy(%struct.pthread_mutex_t*)
; int pthread_mutex_lock(pthread_mutex_t* mutex);
declare %int @pthread_mutex_lock(%struct.pthread_mutex_t*)
; int pthread_mutex_lock(pthread_mutex_t* mutex);
declare %int @pthread_mutex_unlock(%struct.pthread_mutex_t*)
; setOwnerNative(Thread owner) => ()
declare void @shadow.natives..Mutex.Owner_MsetOwnerNative_shadow.standard..Thread(%shadow.natives..Mutex.Owner*, %shadow.standard..Thread*)

; 
declare %void* @extractPointer(%shadow.natives..Pointer*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; initMutex(Pointer ptr) => (int);
define %int @shadow.natives..Mutex_MinitMutex_shadow.natives..Pointer(%shadow.natives..Mutex*, %shadow.natives..Pointer*) {
entry:
	%ptr.addr = call %void* @extractPointer(%shadow.natives..Pointer* %1)
	%handle = bitcast %void* %ptr.addr to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_init(%struct.pthread_mutex_t* %handle, %int* null)
	
	ret %int %call
}

; destroyMutex(Pointer ptr) => (int);
define %int @shadow.natives..Mutex_MdestroyMutex_shadow.natives..Pointer(%shadow.natives..Mutex*, %shadow.natives..Pointer*) {
entry:
	%ptr.addr = call %void* @extractPointer(%shadow.natives..Pointer* %1)
	%handle = bitcast %void* %ptr.addr to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_destroy(%struct.pthread_mutex_t* %handle)

	ret %int %call
}

; unlockMutex(Pointer ptr) => (int);
define %int @shadow.natives..Mutex_MunlockMutex_shadow.natives..Pointer(%shadow.natives..Mutex*, %shadow.natives..Pointer*) {
entry:
	%ptr.addr = call %void* @extractPointer(%shadow.natives..Pointer* %1)
	%handle = bitcast %void* %ptr.addr to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_unlock(%struct.pthread_mutex_t* %handle)
	ret %int %call
}

; lockMutex(Pointer ptr) => (int);
define %int @shadow.natives..Mutex_MlockMutex_shadow.natives..Pointer(%shadow.natives..Mutex*, %shadow.natives..Pointer*) {
entry:
	%ptr.addr = call %void* @extractPointer(%shadow.natives..Pointer* %1)
	%handle = bitcast %void* %ptr.addr to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_lock(%struct.pthread_mutex_t* %handle)
	ret %int %call
}

; get handleSize() => (int);
define %int @shadow.natives..Mutex_MhandleSize(%shadow.natives..Mutex*) {
entry:
	%sizeOfMutex = ptrtoint %struct.pthread_mutex_t* getelementptr (%struct.pthread_mutex_t, %struct.pthread_mutex_t* null, i32 1) to i32
	ret %int %sizeOfMutex
}

; setOwner() => ();
define void @shadow.natives..Mutex_MsetOwner_shadow.natives..Mutex.Owner_shadow.standard..Thread(%shadow.natives..Mutex*, %shadow.natives..Mutex.Owner*, %shadow.standard..Thread*) {
entry:
	call void @shadow.natives..Mutex.Owner_MsetOwnerNative_shadow.standard..Thread(%shadow.natives..Mutex.Owner* %1, %shadow.standard..Thread* %2)
	ret void
}