package com.zyao89.zrouter.model;

/**
 * Created by zyao89 on 2017/3/3.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public enum RouterType
{
    ACTIVITY("android.app.Activity"),
    SERVICE("android.app.Service"),
    CONTENT_PROVIDER("android.app.ContentProvider"),
    BROADCAST("android.content.BroadcastReceiver"),
    METHOD(""),
    FRAGMENT("android.app.Fragment"),
    UNKNOWN("Unknown route type");

    String className;

    public String getClassName() {
        return className;
    }

    RouterType(String className) {
        this.className = className;
    }

    public static RouterType parse(String name) {
        for (RouterType routeType : RouterType.values()) {
            if (routeType.getClassName().equals(name)) {
                return routeType;
            }
        }
        return UNKNOWN;
    }
}
