package cn.evake.auth.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.evake.auth.annotation.LoggerAction;
import cn.evake.auth.dao.model.SysUserLog;
import cn.evake.auth.service.log.LogService;
import cn.evake.auth.service.user.UserService;
import cn.evake.auth.usermodel.UserInfoModel;
import cn.evake.auth.util.ServletUtil;

@Aspect
@Component
public class LogAopAction {
    //本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(LogAopAction.class);

    @Autowired
    private LogService          logService;

    @Autowired
    private UserService         userService;

    //切点
    @Pointcut("@annotation(cn.evake.auth.annotation.LoggerAction)")
    public void controllerAspect() {

    }

    /**
     * 操作异常记录
     *@descript
     *@param point
     *@version 1.0
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     * @throws Throwable 
     */
    @Around("controllerAspect()")
    public Object doController(ProceedingJoinPoint point) throws Throwable {
        try {
            Method method = getMethod(point);
            if (method != null && method.isAnnotationPresent(LoggerAction.class)) {
                String className = point.getTarget().getClass().getName();
                String methodName = point.getSignature().getName();
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
                UserInfoModel userInfo = userService.getUserModelChecked(request);
                LoggerAction annotation = method.getAnnotation(LoggerAction.class);
                String ip = ServletUtil.getIpAddress(request);
                String browser = ServletUtil.getUserAgent(request);
                String requestURI = request.getRequestURI();
                String descpTemp = annotation.description();
                String description = executeTemplate(descpTemp, point);
                SysUserLog userLog = new SysUserLog();
                if (userInfo != null) {
                    userLog.setUuid(userInfo.getUuid());
                    userLog.setUserName(userInfo.getUserName());
                } else {
                    userLog.setUuid(null);
                }
                userLog.setAction(description);
                userLog.setIp(ip);
                userLog.setBrowser(browser);
                userLog.setViewUrl(requestURI);
                userLog.setActionType(annotation.actionType().getType());
                userLog.setGmtCreate(new Date());
                logger.debug("=====通知开始=====");
                logger.debug("请求方法:" + className + "." + methodName + "()");
                logger.debug("方法描述:" + userLog.getAction());
                logger.debug("请求IP:" + userLog.getIp());
                logger.debug("访问URl:" + userLog.getViewUrl());
                logger.debug("=====通知结束=====");
                logService.addNewUserLog(userLog);
            }
        } catch (Throwable e) {
            //记录本地异常日志
            logger.debug("====通知异常====");
            logger.debug("异常信息:{}", e.getMessage());
            throw e;
        }
        return point.proceed();

    }

    /**
     * 
     * 解析SPEL
     * @param template
     * @param joinPoint
     * @return
     */
    private String executeTemplate(String template, ProceedingJoinPoint joinPoint) {
        LocalVariableTableParameterNameDiscoverer parameterNameDiscovere = new LocalVariableTableParameterNameDiscoverer();
        Method method = getMethod(joinPoint);
        String[] parameterNames = parameterNameDiscovere.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        ExpressionParser parser = new SpelExpressionParser();
        Object[] args = joinPoint.getArgs();
        if (args.length == parameterNames.length) {
            for (int i = 0, len = args.length; i < len; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        Expression parseExpression = parser.parseExpression(template, new TemplateParserContext());
        String result = parseExpression.getValue(context, String.class);
        return result;
    }

    /**
     * 获取切面方法
     * @param joinPoint
     * @return
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        try {
            method = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return method;
    }
}