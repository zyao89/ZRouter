package com.zyao89.zrouter.exception;

import static com.zyao89.zrouter.utils.Constant.ROOT_NAME;

/**
 * Created by zyao89 on 2017/3/6.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class ZRouterRuntimeException extends RuntimeException
{
    public ZRouterRuntimeException(String message)
    {
        super(ROOT_NAME + " -->> " +message);
    }

    public ZRouterRuntimeException(String message, Throwable cause)
    {
        super(ROOT_NAME + " -->> " +message, cause);
    }
}
