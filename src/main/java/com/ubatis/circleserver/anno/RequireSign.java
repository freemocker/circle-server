package com.ubatis.circleserver.anno;

import java.lang.annotation.*;

/**
 * 要求验证签名 <br/>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireSign {
    String uidkey() default "";//该值是获取用户id的key，如果有传，要求参数里的用户id要等于header里的uid
}
