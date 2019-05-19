package com.ubatis.circleserver.anno;

import java.lang.annotation.*;

/**
 * Created by lance on 2017/10/17.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetInputs {
    String value() default "";
}
