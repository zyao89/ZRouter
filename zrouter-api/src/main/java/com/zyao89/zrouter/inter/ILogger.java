package com.zyao89.zrouter.inter;

/**
 * Created by zyao89 on 2017/3/5.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public interface ILogger
{
    enum STATUS
    {
        INFO, DEBUG, WARN, ERROR, FULL, NONE;
    }

    void showStackTrace(boolean isShowStackTrace);

    void d(String tag, String message);

    void d(String message);

    void i(String tag, String message);

    void i(String message);

    void w(String tag, String message);

    void w(String message);

    void e(String tag, String message);

    void e(String message);

    void z(String message);

    String getCurrTag();
}
