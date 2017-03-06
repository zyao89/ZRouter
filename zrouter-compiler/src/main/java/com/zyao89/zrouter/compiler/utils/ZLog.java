package com.zyao89.zrouter.compiler.utils;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by zyao89 on 2017/3/3.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class ZLog
{
    private final Messager msg;

    public ZLog(Messager messager)
    {
        msg = messager;
    }

    /**
     * Print info log.
     */
    public void info(CharSequence info)
    {
        if (ZUtils.isNotEmpty(info))
        {
            final String text = String.format(">>> ------ %s ------ <<<", info);
            msg.printMessage(Diagnostic.Kind.NOTE, Constant.PREFIX_OF_LOGGER + text);
        }
    }

    public void error(CharSequence error)
    {
        if (ZUtils.isNotEmpty(error))
        {
            final String text = String.format(">>> xxxxxx * %s * xxxxxx <<<", "An exception is encountered, [" + error + "]");
            msg.printMessage(Diagnostic.Kind.ERROR, Constant.PREFIX_OF_LOGGER + text);
        }
    }

    public void error(Throwable error)
    {
        if (null != error)
        {
            final String text = String.format(">>> xxxxxx * %s * xxxxxx <<<", "An exception is encountered, [" + error.getMessage() + "]");
            msg.printMessage(Diagnostic.Kind.ERROR, Constant.PREFIX_OF_LOGGER + text + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    public void warning(CharSequence warning)
    {
        if (ZUtils.isNotEmpty(warning))
        {
            final String text = String.format(">>> ====== * %s * ====== <<<", warning);
            msg.printMessage(Diagnostic.Kind.WARNING, Constant.PREFIX_OF_LOGGER + text);
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTrace)
    {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace)
        {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


}
