package org.deslre.annotation;

import java.lang.annotation.*;

/**
 * ClassName: AuthCheck
 * Description: 身份校验
 * Author: Deslrey
 * Date: 2025-06-11 8:47
 * Version: 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 是否需要是管理员
     */
    boolean admin() default false;

    /**
     * 校验登录
     */
    boolean checkLogin() default true;

    /**
     * 操作日志描述（为空表示不记录日志）
     */
    String log() default "";

    /**
     * 日志分类（用于区分功能模块，如“文章管理”、“用户管理”等）
     */
    String category() default "未分类";
}
