package com.zyao89.zrouter.core;

import android.content.Context;

import com.zyao89.zrouter.ZRouter;
import com.zyao89.zrouter.exception.ZRouterRuntimeException;
import com.zyao89.zrouter.model.RouterProperty;
import com.zyao89.zrouter.utils.ClazzUtil;
import com.zyao89.zrouter.utils.ReflectUtil;
import com.zyao89.zrouter.utils.ZLog;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.zyao89.zrouter.utils.Constant.DOT;
import static com.zyao89.zrouter.utils.Constant.INJECT_NAME_METHOD;
import static com.zyao89.zrouter.utils.Constant.ROOT_NAME;
import static com.zyao89.zrouter.utils.Constant.SEPARATOR;
import static com.zyao89.zrouter.utils.Constant.SUFFIX_GROUP;
import static com.zyao89.zrouter.utils.Constant.SUFFIX_ROOT;
import static com.zyao89.zrouter.utils.Constant.Z_ROUTER_ROOT_PACKAGE;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public final class LogicCenter
{
    private static WeakReference<Context> mContext;

    private LogicCenter() {}

    public static synchronized void init(Context context) throws ZRouterRuntimeException
    {
        mContext = new WeakReference<>(context);

        try
        {
            List<String> classFileNames = ClazzUtil.getFileNameByPackageName(context, Z_ROUTER_ROOT_PACKAGE);
            for (String className : classFileNames)
            {
                if (className.startsWith(Z_ROUTER_ROOT_PACKAGE + DOT + ROOT_NAME + SEPARATOR + SUFFIX_ROOT + SEPARATOR))
                {
                    // This one of root elements, load root.
                    Class<?> aClass = Class.forName(className);
                    Method method = ReflectUtil.getMethod(aClass, INJECT_NAME_METHOD, Map.class);
                    if (method != null)
                    {
                        method.invoke(null, DataCenter.rootMap);
                    }
                }
                else if (className.startsWith(Z_ROUTER_ROOT_PACKAGE + DOT + ROOT_NAME + SEPARATOR + SUFFIX_GROUP + SEPARATOR))
                {
                    Class<?> aClass = Class.forName(className);
                    Object instance = ReflectUtil.newInstance(aClass);
                    Method method = ReflectUtil.getMethod(aClass, INJECT_NAME_METHOD, Map.class);
                    ReflectUtil.invokeMethod(instance, method, DataCenter.nameMap);
                }
            }

            if (DataCenter.rootMap.size() == 0)
            {
                ZLog.with(ROOT_NAME).e("No files were found, check your configuration please!");
            }

            if (ZRouter.debuggable())
            {
                ZLog.with(ROOT_NAME).d(String.format(Locale.getDefault(), "LogicCenter has already been loaded, NameMap[%d], RootMap[%d]", DataCenter.nameMap.size(), DataCenter.rootMap.size()));
            }
        }
        catch (Exception e)
        {
            throw new ZRouterRuntimeException("init logic center exception! [" + e.getMessage() + "]");
        }
    }

    public static synchronized void recycle()
    {
        mContext.clear();
        mContext = null;
        DataCenter.clear();
    }

    public static synchronized ZPasser findRouterProperty(String name)
    {
        RouterProperty routerProperty = DataCenter.nameMap.get(name);
        return new ZPasser();
    }
}
