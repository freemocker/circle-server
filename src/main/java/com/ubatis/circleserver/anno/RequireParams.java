package com.ubatis.circleserver.anno;

import java.lang.annotation.*;

/** 声明必需要的参数
 * Created by lance on 2017/4/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireParams {

    String value() default "";

}
