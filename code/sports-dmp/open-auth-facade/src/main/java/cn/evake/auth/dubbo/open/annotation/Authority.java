package cn.evake.auth.dubbo.open.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 权限校验注解
 * @author wang yi
 * @desc 
 * @version $Id: Authority.java, v 0.1 2018年2月26日 下午1:20:54 wang yi Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Authority {

    // 默认验证
    AuthorityTypeEnum value() default AuthorityTypeEnum.Validate;

    //授权码
    String permissionCode();

}