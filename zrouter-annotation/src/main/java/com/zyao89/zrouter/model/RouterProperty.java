package com.zyao89.zrouter.model;


import com.zyao89.zrouter.annotation.ZRouter;

import javax.lang.model.element.Element;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public final class RouterProperty
{
    private RouterType mRouterType;         // Type of router
    private Element    mElement;        // Raw type of route
    private Class<?>   mTargetClz; // Target
    private String     mName;            // Name of route
    private String     mGroup;           // Group of route
    private String     mDescription;   // Description
    private String     mPath; // Path
    private String     mModuleName; //ModuleName

    public static RouterProperty build(RouterType routerType, Class<?> targetClz, String name, String group, String description, String path, String moduleName)
    {
        return new RouterProperty(routerType, null, targetClz, name, group, description, path, moduleName);
    }

    public RouterProperty(RouterType routerType, Element element, ZRouter zRouter, String moduleName)
    {
        this(routerType, element, zRouter.name(), zRouter.group(), zRouter.description(), moduleName);
    }

    public RouterProperty(RouterType routerType, Element element, String name, String group, String description, String moduleName)
    {
        this(routerType, element, name, group, description, null, moduleName);
    }

    public RouterProperty(RouterType routerType, Element element, String name, String group, String description, String path, String moduleName)
    {
        this(routerType, element, null, name, group, description, path, moduleName);
    }

    public RouterProperty(RouterType routerType, Element element, Class<?> targetClz, String name, String group, String description, String path, String moduleName)
    {
        mRouterType = routerType;
        mElement = element;
        mTargetClz = targetClz;
        mName = name;
        mGroup = group;
        mDescription = description;
        mPath = path;
        mModuleName = moduleName;
    }

    public RouterType getRouterType()
    {
        return mRouterType;
    }

    public Element getElement()
    {
        return mElement;
    }

    public String getName()
    {
        return mName;
    }

    public String getGroup()
    {
        return mGroup;
    }

    public void setGroup(String group)
    {
        mGroup = group;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public String getPath()
    {
        return mPath;
    }

    public void setPath(String path)
    {
        mPath = path;
    }

    public Class<?> getTargetClz()
    {
        return mTargetClz;
    }

    public String getModuleName()
    {
        return mModuleName;
    }
}
