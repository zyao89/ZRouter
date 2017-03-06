package com.zyao89.zrouter.inter;

import com.zyao89.zrouter.model.RouterProperty;

import java.util.Map;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public interface IZRouterGroup
{
    void inject(Map<String, RouterProperty> groupMap);
}
