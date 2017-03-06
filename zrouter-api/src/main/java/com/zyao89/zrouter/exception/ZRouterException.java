package com.zyao89.zrouter.exception;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class ZRouterException extends Exception
{
    private static final String PREFIX = "***【ZRouter Exception】: ";

    public ZRouterException(String message)
    {
        super(PREFIX + message);
    }

    public ZRouterException(String message, Throwable cause)
    {
        super(PREFIX + message, cause);
    }
}
