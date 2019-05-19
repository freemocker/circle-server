package com.ubatis.circleserver.anno;

import java.lang.annotation.*;

/** 需要传token和id <br/>
 * latest token类型
 * Created by lance on 2017/4/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLatestToken {
    //该值是获取用户id的key，如果有传，要求参数里的用户id要等于header里的uid
    // 要求字段在参数里唯一，不然会有类型不一致，或者注入错误的bug
    String uidkey() default "";
}
