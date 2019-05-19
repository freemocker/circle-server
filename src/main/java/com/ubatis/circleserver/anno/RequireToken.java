package com.ubatis.circleserver.anno;

import java.lang.annotation.*;

/** 需要传token和id <br/>
 *  token,refresh token类型
 * Created by lance on 2017/4/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireToken {

}
