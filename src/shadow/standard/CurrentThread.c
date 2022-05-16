/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <standard/CurrentThread.h>

// METHOD SIGNATURES //
shadow_boolean_t __ShadowCurrentThread_Yield(void);
// METHOD SIGNATURES //


#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	shadow_boolean_t __ShadowCurrentThread_Yield(void)
	{
		Sleep(0);
		return true;
	}
#else
	#include <sched.h>

	shadow_boolean_t __ShadowCurrentThread_Yield(void)
	{
		return sched_yield() == 0;
	}
#endif