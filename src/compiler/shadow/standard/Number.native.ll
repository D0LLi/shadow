attributes #0 = { alwaysinline nounwind readnone }


; shadow.standard@Boolean native methods


; shadow.standard@Code native methods


; shadow.standard@Byte native methods

define i8 @_Pshadow_Pstandard_Cbyte_MbitOr_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = or i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitXor_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = xor i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitAnd_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = and i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = add i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = sub i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = mul i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = sdiv i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = srem i8 %0, %1
	ret i8 %3
}

define i32 @_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cbyte_Mequal_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = icmp eq i8 %0, %1
	ret i1 %3
}


; shadow.standard@UByte native methods

@_Pshadow_Pstandard_Cubyte_MbitOr_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_MbitOr_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_MbitXor_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_MbitXor_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_MbitAnd_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_MbitAnd_Pshadow_Pstandard_Cbyte

@_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte
define i8 @_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte(i8, i8) #0 {
	%3 = udiv i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte(i8, i8) #0 {
	%3 = urem i8 %0, %1
	ret i8 %3
}

define i32 @_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Cubyte_Mequal_Pshadow_Pstandard_Cubyte = alias i1 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Mequal_Pshadow_Pstandard_Cbyte


; shadow.standard@Short native methods

define i16 @_Pshadow_Pstandard_Cshort_MbitOr_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = or i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_MbitXor_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = xor i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_MbitAnd_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = and i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = add i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = sub i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = mul i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = sdiv i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = srem i16 %0, %1
	ret i16 %3
}

define i32 @_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cshort_Mequal_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = icmp eq i16 %0, %1
	ret i1 %3
}


; shadow.standard@UShort native methods

@_Pshadow_Pstandard_Cushort_MbitOr_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_MbitOr_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_MbitXor_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_MbitXor_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_MbitAnd_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_MbitAnd_Pshadow_Pstandard_Cshort

@_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort
define i16 @_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort(i16, i16) #0 {
	%3 = udiv i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort(i16, i16) #0 {
	%3 = urem i16 %0, %1
	ret i16 %3
}

define i32 @_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Cushort_Mequal_Pshadow_Pstandard_Cushort = alias i1 (i16, i16)* @_Pshadow_Pstandard_Cshort_Mequal_Pshadow_Pstandard_Cshort


; shadow.standard@Int native methods

define i32 @_Pshadow_Pstandard_Cint_MbitOr_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = or i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_MbitXor_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = xor i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_MbitAnd_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = and i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = add i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = sub i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = mul i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = sdiv i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = srem i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cint_Mequal_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = icmp eq i32 %0, %1
	ret i1 %3
}


; shadow.standard@UInt native methods

@_Pshadow_Pstandard_Cuint_MbitOr_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitOr_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_MbitXor_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitXor_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_MbitAnd_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitAnd_Pshadow_Pstandard_Cint

@_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
define i32 @_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = udiv i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = urem i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Cuint_Mequal_Pshadow_Pstandard_Cuint = alias i1 (i32, i32)* @_Pshadow_Pstandard_Cint_Mequal_Pshadow_Pstandard_Cint


; shadow.standard@Long native methods

define i64 @_Pshadow_Pstandard_Clong_MbitOr_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = or i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_MbitXor_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = xor i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_MbitAnd_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = and i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = add i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = sub i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = mul i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = sdiv i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = srem i64 %0, %1
	ret i64 %3
}

define i32 @_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Clong_Mequal_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = icmp eq i64 %0, %1
	ret i1 %3
}


; shadow.standard@ULong native methods

@_Pshadow_Pstandard_Culong_MbitOr_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_MbitOr_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_MbitXor_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_MbitXor_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_MbitAnd_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_MbitAnd_Pshadow_Pstandard_Clong

@_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
define i64 @_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong(i64, i64) #0 {
	%3 = udiv i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong(i64, i64) #0 {
	%3 = urem i64 %0, %1
	ret i64 %3
}

define i32 @_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Culong_Mequal_Pshadow_Pstandard_Culong = alias i1 (i64, i64)* @_Pshadow_Pstandard_Clong_Mequal_Pshadow_Pstandard_Clong


; shadow.standard@Float native methods

define float @_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fadd float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fsub float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fmul float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fdiv float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = frem float %0, %1
	ret float %3
}

define i32 @_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fcmp ueq float %0, %1
	%4 = fcmp olt float %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cfloat_Mequal_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fcmp ord float %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq float %0, %1
	ret i1 %5
	%7 = fcmp uno float %0, 0.
	%8 = fcmp uno float %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i32 @_Pshadow_Pstandard_Cfloat_Mraw(float) #0 {
	%2 = bitcast float %0 to i32
	ret i32 %2
}

; shadow.standard@Double native methods

define double @_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fadd double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fsub double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fmul double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fdiv double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = frem double %0, %1
	ret double %3
}

define i32 @_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fcmp ueq double %0, %1
	%4 = fcmp olt double %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cdouble_Mequal_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fcmp ord double %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq double %0, %1
	ret i1 %5
	%7 = fcmp uno double %0, 0.
	%8 = fcmp uno double %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i64 @_Pshadow_Pstandard_Cdouble_Mraw(double) #0 {
	%2 = bitcast double %0 to i64
	ret i64 %2
}
