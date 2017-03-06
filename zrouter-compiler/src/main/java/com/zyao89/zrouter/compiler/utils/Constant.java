package com.zyao89.zrouter.compiler.utils;

/**
 * Created by zyao89 on 2017/3/3.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public interface Constant
{
    // Common
    String SEPARATOR = "$$";
    String PROJECT = "ZRouter";
    String WARNING_TIPS = "DO NOT EDIT THIS FILE!!! This is ZRouter automatically generated file.";
    String PACKAGE_NAME = "com.zyao89.zrouter";

    // System interface
    String ACTIVITY = "android.app.Activity";
    String FRAGMENT = "android.app.Fragment";
    String FRAGMENT_V4 = "android.support.v4.app.Fragment";
    String SERVICE = "android.app.Service";

    // Z interface
    String I_ZROUTER_INTER_ROOT = PACKAGE_NAME + ".inter";
    String I_ZROUTER_GROUP = I_ZROUTER_INTER_ROOT + ".IZRouterGroup";

    // Java type
    String LANG = "java.lang";
    String BYTE = LANG + ".Byte";
    String SHORT = LANG + ".Short";
    String INTEGER = LANG + ".Integer";
    String LONG = LANG + ".Long";
    String FLOAT = LANG + ".Float";
    String DOUBEL = LANG + ".Double";
    String BOOLEAN = LANG + ".Boolean";
    String STRING = LANG + ".String";

    // AUTO Inject
    String AUTO_CREATE_FILE_PACKAGE = PACKAGE_NAME + ".auto";
    String AUTO_CREATE_INJECT_NAME_METHOD = "inject";
    String AUTO_CREATE_INJECT_ROUTER_NAME_PARAM = "routesMap";
    String AUTO_CREATE_INJECT_GROUP_NAME_PARAM = "groupMap";
    String AUTO_CREATE_INJECT_CLASS_NAME_OF_GROUP = PROJECT + SEPARATOR + "Group" + SEPARATOR;
    String AUTO_CREATE_INJECT_CLASS_NAME_OF_ROOT = PROJECT + SEPARATOR + "Root" + SEPARATOR;

    // Default Params
    String DEFAULT_GROUP = "undefined";

    // Log
    String PREFIX_OF_LOGGER = PROJECT + "::Compiler ";

    // Annotation type
    String ANNOTATION_TYPE_ZROUTER = PACKAGE_NAME + ".annotation.ZRouter";

    // others
    String GRADLE_KEY_MODULE_NAME = "moduleName";
}