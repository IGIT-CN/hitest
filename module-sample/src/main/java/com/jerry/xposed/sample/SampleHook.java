package com.jerry.xposed.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SampleHook implements IXposedHookLoadPackage {
	private final static String TAG = SampleHook.class.getSimpleName();
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		XposedHelpers.findAndHookMethod("android.app.Activity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				Activity activity = (Activity) param.thisObject;
				String activityName = activity.getClass().getName();
				Log.e(TAG, "Activity onCreate:" + activityName);
			}
		});
		XposedHelpers.findAndHookMethod("android.location.LocationManager", lpparam.classLoader, "getLastKnownLocation", String.class, new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
				Log.e(TAG, "call getLastKnownLocation");
				Log.d(TAG, Log.getStackTraceString(new Throwable()));
			}
		});
	}
}
