; Shadow Library

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
%size_t = type i8*

; standard definitions
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, %shadow.standard..Array*, %shadow.standard..Array* }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods* , %long }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods* , %long }

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_methods = external constant %shadow.standard..OutOfMemoryException_methods
@ubyte_A_class = external constant %shadow.standard..GenericClass
@shadow.standard..String_A_class = external constant %shadow.standard..GenericClass

%shadow.io..Console_methods = type opaque
@shadow.io..Console_methods = external constant %shadow.io..Console_methods
@shadow.io..Console_class = external constant %shadow.standard..Class
%shadow.io..Console = type opaque
@shadow.io..Console_instance = external thread_local global %shadow.io..Console*

declare %shadow.io..Console* @shadow.io..Console_Mcreate(%shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console_MprintErrorLine(%shadow.io..Console*)
declare %shadow.io..Console* @shadow.io..Console_MprintErrorLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.standard..String* @shadow.standard..String_Mcreate_ubyte_A(%shadow.standard..Object*, %shadow.standard..Array*)

;declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 

declare %size_t @strlen(i8* nocapture)
declare i8* @strncpy(i8*, i8* nocapture, %size_t)

%shadow.test..Test = type opaque
%shadow.test..Test_methods = type opaque
@shadow.test..Test_methods = external constant %shadow.test..Test_methods
@shadow.test..Test_class = external constant %shadow.standard..Class
declare %shadow.test..Test* @shadow.test..Test_Mcreate(%shadow.standard..Object*)
declare void @shadow.test..Test_Mmain_shadow.standard..String_A(%shadow.test..Test*, %shadow.standard..Array*)

declare i32 @__shadow_personality_v0(...)
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..GenericClass* %class, %ulong %longElements, %boolean %nullable)

define i32 @main(i32 %argc, i8** %argv) personality i32 (...)* @__shadow_personality_v0 {
_start:	
	%uninitializedConsole = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..Console_class, %shadow.standard..Object_methods* bitcast(%shadow.io..Console_methods* @shadow.io..Console_methods to %shadow.standard..Object_methods*) )
	%console = call %shadow.io..Console* @shadow.io..Console_Mcreate(%shadow.standard..Object* %uninitializedConsole)
    store %shadow.io..Console* %console, %shadow.io..Console** @shadow.io..Console_instance	
	%countInt = sub i32 %argc, 1	
	%count = zext %int %countInt to %long
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..GenericClass* @shadow.standard..String_A_class, %long %count, %boolean false)
	%stringRef = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%stringArray = bitcast %shadow.standard..Array* %stringRef to %shadow.standard..String**
	br label %_loopTest
_loopBody:
	%allocatedString = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..String_methods* @shadow.standard..String_methods to %shadow.standard..Object_methods*))	
	%length = call %size_t @strlen(i8* nocapture %nextArg)	
	%longLength = ptrtoint %size_t %length to %ulong
	%allocatedArray = call %shadow.standard..Array* @__allocateArray(%shadow.standard..GenericClass* @ubyte_A_class, %long %longLength, %boolean false)	
	%byteArray = getelementptr %shadow.standard..Array, %shadow.standard..Array* %allocatedArray, i32 1
	%bytes = bitcast %shadow.standard..Array* %byteArray to i8*
	call i8* @strncpy(i8* %bytes, i8* nocapture %nextArg, %size_t %length)	
	%string = call %shadow.standard..String* @shadow.standard..String_Mcreate_ubyte_A(%shadow.standard..Object* %allocatedString, %shadow.standard..Array* %allocatedArray)
	%byteArrayAsObj = bitcast %shadow.standard..Array* %byteArray to %shadow.standard..Object*
	call void @__decrementRef(%shadow.standard..Object* %byteArrayAsObj) nounwind
	store %shadow.standard..String* %string, %shadow.standard..String** %stringPhi
	%nextString = getelementptr %shadow.standard..String*, %shadow.standard..String** %stringPhi, i32 1
	br label %_loopTest
_loopTest:
	%argPhi = phi i8** [ %argv, %_start ], [ %nextArgPointer, %_loopBody ]
	%stringPhi = phi %shadow.standard..String** [ %stringArray, %_start ], [ %nextString, %_loopBody ]
	%nextArgPointer = getelementptr i8*, i8** %argPhi, i32 1
	%nextArg = load i8*, i8** %nextArgPointer
	%done = icmp eq i8* %nextArg, null
	br i1 %done, label %_loopEnd, label %_loopBody	
_loopEnd:	
	%object = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.test..Test_class, %shadow.standard..Object_methods* bitcast(%shadow.test..Test_methods* @shadow.test..Test_methods to %shadow.standard..Object_methods*))		
	%initialized = call %shadow.test..Test* @shadow.test..Test_Mcreate(%shadow.standard..Object* %object)
	invoke void @callMain(%shadow.test..Test* %initialized, %shadow.standard..Array* %array)
			to label %_success unwind label %_exception
_success:		
	call void @__decrementRef(%shadow.standard..Object* %object) nounwind
	%arrayAsObj = bitcast %shadow.standard..Array* %array to %shadow.standard..Object*
	call void @__decrementRef(%shadow.standard..Object* %arrayAsObj) nounwind	
	store %shadow.io..Console* null, %shadow.io..Console** @shadow.io..Console_instance		
	%consoleAsObj = bitcast %shadow.io..Console* %console to %shadow.standard..Object*
    call void @__decrementRef(%shadow.standard..Object* %consoleAsObj) nounwind
	ret i32 0
_exception:
	%caught = landingpad { i8*, i32 }
            catch %shadow.standard..Class* @shadow.standard..Exception_class
	%data = extractvalue { i8*, i32 } %caught, 0
	%exception = call %shadow.standard..Exception* @__shadow_catch(i8* nocapture %data) nounwind
	; Console already initialized		
	%exceptionAsObject = bitcast %shadow.standard..Exception* %exception to %shadow.standard..Object*	
	call %shadow.io..Console* @shadow.io..Console_MprintErrorLine_shadow.standard..Object(%shadow.io..Console* %console, %shadow.standard..Object* %exceptionAsObject )
	ret i32 1
}

%shadow.standard..Thread = type opaque
declare %shadow.standard..Thread* @shadow.standard..Thread_MinitMainThread()
declare void @shadow.standard..Thread_MwaitForThreads(%shadow.standard..Thread*)
define void @callMain(%shadow.test..Test* %initialized, %shadow.standard..Array* %args) {
entry:
	%mainThread = call %shadow.standard..Thread* @shadow.standard..Thread_MinitMainThread()
	call void @shadow.test..Test_Mmain_shadow.standard..String_A(%shadow.test..Test* %initialized, %shadow.standard..Array* %args)
	call void @shadow.standard..Thread_MwaitForThreads(%shadow.standard..Thread* %mainThread)
	
	%threadAsObj = bitcast %shadow.standard..Thread* %mainThread to %shadow.standard..Object*
	call void @__decrementRef(%shadow.standard..Object* %threadAsObj) nounwind	
	
	ret void
}