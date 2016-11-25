package com.itheima.googleplay;

import android.app.Application;
import android.content.Context;
/**
 * 代表当前应用程序
 * @author yin
 *
 */
public class BaseApplication extends Application {
	private static BaseApplication application;
	@Override
	public void onCreate() {
		super.onCreate();
		application=this;

	}
	public static Context getApplication() {
		return application;
	}


}
