package cn.evake.auth.web.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.exception.AuthException;
import cn.evake.auth.service.log.LogService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.util.ServletUtil;
import cn.evake.auth.util.annotion.AuthAnnotationUtil;
import cn.evake.auth.util.user.UserInfoUtil;

/**
 * 权限认证拦截器
 * @author wang yi
 * @desc 
 * @version $Id: AuthorityAnnotationInterceptor.java, v 0.1 2018年2月22日 下午7:20:12 wang yi Exp $
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

    private Logger      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private LogService  logservice;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.debug("用户请求--->" + requestURI);
        //放行所有html静态文件
        if (request.getRequestURI().contains(".html")) {
            return true;
        }
        //是否放行用户
        boolean isPermit = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Class<?> clazz = hm.getBeanType();
            Method m = hm.getMethod();
            if (clazz != null && m != null) {
                Authority authority = AuthAnnotationUtil.getAnthAnnotation(hm);
                if (authority != null) {
                    if (AuthorityTypeEnum.NoAuthority == authority.value()) {
                        // 不验证权限，不验证是否登录
                        isPermit = true;
                    }
                    if (AuthorityTypeEnum.NoValidate == authority.value()) {
                        // 验证权限，验证是否登录
                        isPermit = userService.checkIsLogin(request);
                    }
                    //当开启验证以及校验 去校验数据
                    if (AuthorityTypeEnum.Validate == authority.value()) {
                        // 验证登录及权限
                        String permissionCode = authority.permissionCode();
                        isPermit = userService.checkUserLimit(request, permissionCode);
                        String logAction = String.format("用户: %s 访问url : %s 授权码: %s  权限状态: %s",
                            UserInfoUtil.getUserName(request), requestURI, permissionCode,
                            isPermit == true ? "通过" : "非法访问");
                        logger.info(logAction);
                    }
                } else {
                    isPermit = true;
                }
                // 未通过验证
                if (!isPermit) {
                    throw new AuthException("非法访问");
                }
            }
        }
        return true;

    }

    /** 
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse reponse, Object handler,
                           ModelAndView model) throws Exception {
        final String ctx = ServletUtil.getCtx(request);
        request.setAttribute("ctx", ctx);
    }

    /** 
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o,
                                Exception e) throws Exception {

    }
}