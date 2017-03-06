package com.zyao89.zrouter;

import android.content.Context;
import android.text.TextUtils;

import com.zyao89.zrouter.core.LogicCenter;
import com.zyao89.zrouter.core.ZPasser;
import com.zyao89.zrouter.exception.ZRouterRuntimeException;
import com.zyao89.zrouter.inter.ILogger;
import com.zyao89.zrouter.model.RouterProperty;
import com.zyao89.zrouter.utils.ZLog;

import java.lang.ref.WeakReference;

import static com.zyao89.zrouter.utils.Constant.ROOT_NAME;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
final class _Z_Helper
{
    private static WeakReference<Context> mContext;
    private static boolean sDebuggable = false;
    static final ILogger sZLog = ZLog.with(ROOT_NAME);
    static final _Z_Helper sInstance = new _Z_Helper();

    private _Z_Helper() {}

    static void initBefore()
    {

    }

    static synchronized void init(Context context)
    {
        mContext = new WeakReference<>(context);
        LogicCenter.init(context);
        sZLog.i("ZRouter init success!");
    }

    static void initAfter()
    {

    }

    static synchronized boolean debuggable()
    {
        return sDebuggable;
    }

    static synchronized void openDebug()
    {
        sDebuggable = true;
        sZLog.i("ZRouter OPEN DEBUG...");
    }

    static synchronized void openLog()
    {
        ZLog.setStatus(ILogger.STATUS.FULL);
        sZLog.i("ZRouter Log openLog...");
    }

    static boolean isHasInit()
    {
        return mContext != null;
    }

    static void recyclePre()
    {
        mContext.clear();
        mContext = null;
    }

    static void recycle()
    {
        LogicCenter.recycle();
    }

    public ZPasser build(String name)
    {
        if (TextUtils.isEmpty(name)){
            throw new ZRouterRuntimeException("name is invalid!");
        }
        LogicCenter.findRouterProperty(name);
        return null;
    }
}
