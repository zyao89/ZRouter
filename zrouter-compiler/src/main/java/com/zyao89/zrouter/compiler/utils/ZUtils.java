package com.zyao89.zrouter.compiler.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by zyao89 on 2017/3/3.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class ZUtils
{
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(final Map<?,?> map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

}
