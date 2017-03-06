package com.zyao89.zrouter.core;

import com.zyao89.zrouter.inter.IZRouterGroup;
import com.zyao89.zrouter.model.RouterProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
final class DataCenter
{
    // groupName, class
    static Map<String, Class<? extends IZRouterGroup>> rootMap = new HashMap<>();
    // name, params
    static Map<String, RouterProperty>                 nameMap = new HashMap<>();

    static synchronized void clear()
    {
        nameMap.clear();
        rootMap.clear();
    }
}
