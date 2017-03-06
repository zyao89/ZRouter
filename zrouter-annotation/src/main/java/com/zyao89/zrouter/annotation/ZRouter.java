package com.zyao89.zrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zyao89 on 2017/3/3.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface ZRouter
{
    /**
     * 组
     * @return
     */
    String group() default "";

    /**
     * 名称
     * @return
     */
    String name() default "";

    /**
     * 描述
     * @return
     */
    String description() default "";
}
