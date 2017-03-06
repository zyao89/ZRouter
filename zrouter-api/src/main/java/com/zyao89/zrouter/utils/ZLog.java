package com.zyao89.zrouter.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zyao89.zrouter.inter.ILogger;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class ZLog implements ILogger
{
    private static final String MSG = "[" + Constant.ROOT_NAME + "] LOG >>>> \n";

    /**
     * 最大显示数
     */
    private static final int     MAX_SHOW_LENGTH_NUM = 3;
    private static final int     MIN_STACK_OFFSET    = 3;
    private static       ILogger mSingle             = null;
    private static       String  mTAG                = null;
    private static       STATUS  mStatus             = STATUS.NONE;
    private              boolean mIsShowStackTrace   = true;

    private ZLog() {}

    /**
     * 设置是否打开日志(默认打开)
     *
     * @param status DEBUG, WARN, ERROR, FULL, NONE
     */
    public static void setStatus(STATUS status)
    {
        mStatus = status;
    }

    public static STATUS getStatus()
    {
        return mStatus;
    }

    public static ILogger with(@NonNull Object tag)
    {
        if (mSingle == null)
        {
            synchronized (ZLog.class)
            {
                if (mSingle == null)
                {
                    mSingle = new ZLog();
                }
            }
        }
        synchronized (ZLog.class)
        {
            mTAG = tag.getClass().getSimpleName();
        }
        return mSingle;
    }

    @Override
    public void showStackTrace(boolean isShowStackTrace)
    {
        mIsShowStackTrace = isShowStackTrace;
    }

    @Override
    public void d(String tag, String message)
    {
        mTAG = tag;
        d(message);
    }

    @Override
    public void d(String message)
    {
        logHeaderContent(STATUS.DEBUG, message);
    }

    @Override
    public void i(String tag, String message)
    {
        mTAG = tag;
        i(message);
    }

    @Override
    public void i(String message)
    {
        logHeaderContent(STATUS.INFO, message);
    }

    @Override
    public void w(String tag, String message)
    {
        mTAG = tag;
        w(message);
    }

    @Override
    public void w(String message)
    {
        logHeaderContent(STATUS.WARN, message);
    }

    @Override
    public void e(String tag, String message)
    {
        mTAG = tag;
        e(message);
    }

    @Override
    public void e(String message)
    {
        logHeaderContent(STATUS.ERROR, message);
    }

    @Override
    public void z(String message)
    {
        logChunk(STATUS.DEBUG, message);
    }

    @Override
    public String getCurrTag()
    {
        return mTAG;
    }

    private void logHeaderContent(STATUS logType, String msg)
    {
        if (mStatus == STATUS.NONE)
        {
            return;
        }

        if (mStatus.ordinal() < logType.ordinal())
        {
            return;
        }

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String level = "  ";

        int stackOffset = getStackOffset(trace);

        int methodCount = mIsShowStackTrace ? MAX_SHOW_LENGTH_NUM : 0;

        if (logType.ordinal() < STATUS.WARN.ordinal())
        {//INFO or DEBUG is not printStack
            methodCount = 0;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(msg);//打印信息
        builder.append("\n");
        for (int i = methodCount; i > 0; i--)
        {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length)
            {
                continue;
            }
            builder.append("==>");
            builder.append(level).append(getSimpleClassName(trace[stackIndex].getClassName())).append(".").append(trace[stackIndex].getMethodName()).append(" ").append(" (").append(trace[stackIndex].getFileName()).append(":").append(trace[stackIndex].getLineNumber()).append(")");
            builder.append("\n");
            level += "  ";
        }
        logChunk(logType, builder.toString());
    }

    private void logChunk(STATUS logType, String msg)
    {
        if (mStatus == STATUS.NONE)
        {
            return;
        }

        if (mStatus.ordinal() < logType.ordinal())
        {
            return;
        }

        msg = "------------------------------------------------\n" +
                "|-->     " + msg +
                "------------------------------------------------\n";

        switch (logType)
        {
            case ERROR:
                Log.e(mTAG, MSG + msg);
                break;
            case WARN:
                Log.w(mTAG, MSG + msg);
                break;
            case DEBUG:
                Log.d(mTAG, MSG + msg);
                break;
            case INFO:
                Log.i(mTAG, MSG + msg);
                break;
        }
    }

    private String getSimpleClassName(String name)
    {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace)
    {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++)
        {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(ZLog.class.getName()) && !name.equals(ILogger.class.getName()))
            {
                return --i;
            }
        }
        return -1;
    }
}
