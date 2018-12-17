package cn.evake.auth.dubbo.open.annotation;

/**
 * 权限认证枚举
 * @author wang yi
 * @desc 
 * @version $Id: AuthorityType.java, v 0.1 2018年2月22日 下午7:18:44 wang yi Exp $
 */
public enum AuthorityTypeEnum {

    /**
     *登录和权限都验证
     */
    Validate,
    /**
     *校验登陆但不验证权限
     */
    NoValidate,
    /**
     *不校验登陆不验证权限
     */
    NoAuthority;
}