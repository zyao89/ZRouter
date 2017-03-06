package com.zyao89.zrouter.test;

import android.app.Application;

import com.zyao89.zrouter.ZRouter;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        ZRouter.openLog();
        ZRouter.openDebug();
        ZRouter.init(this);
    }
}
