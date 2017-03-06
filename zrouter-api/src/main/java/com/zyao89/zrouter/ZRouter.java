package com.zyao89.zrouter;

import android.app.Application;

import com.zyao89.zrouter.core.ZPasser;
import com.zyao89.zrouter.exception.InitException;
import com.zyao89.zrouter.utils.Constant;

/**
 * Created by zyao89 on 2017/3/3.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class ZRouter
{
    private volatile static ZRouter mSingle = null;

    private ZRouter()
    {

    }

    public static synchronized void init(Application application)
    {
        _Z_Helper.sZLog.i("ZRouter init start...");
        _Z_Helper.initBefore();
        _Z_Helper.init(application);
        _Z_Helper.initAfter();
        _Z_Helper.sZLog.i("ZRouter init end...");
    }

    public static boolean debuggable()
    {
        return _Z_Helper.debuggable();
    }

    public static void openDebug()
    {
        _Z_Helper.openDebug();
    }

    public static void openLog()
    {
        _Z_Helper.openLog();
    }

    public static ZRouter getInstance()
    {
        if (!_Z_Helper.isHasInit())
        {
            throw new InitException(Constant.ROOT_NAME + " init is exception! PLEASE init(context) first!!");
        }
        else
        {
            if (mSingle == null)
            {
                synchronized (ZRouter.class)
                {
                    if (mSingle == null)
                    {
                        mSingle = new ZRouter();
                    }
                }
            }
            return mSingle;
        }
    }

    public void recycle(){
        _Z_Helper.recyclePre();
        _Z_Helper.recycle();
        _Z_Helper.sZLog.i("ZRouter recycle success...");
    }

    public ZPasser build(String name){
        return _Z_Helper.sInstance.build(name);
    }
}
