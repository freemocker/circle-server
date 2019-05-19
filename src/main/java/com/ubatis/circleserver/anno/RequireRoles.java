package com.ubatis.circleserver.anno;

import java.lang.annotation.*;

/** 需要的角色。或关系，逗号隔开
 * Created by lance on 2017/4/27.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRoles {

    String value() default "";

    String remark() default "";

}
