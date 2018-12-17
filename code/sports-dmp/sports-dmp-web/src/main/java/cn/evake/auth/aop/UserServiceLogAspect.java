package cn.evake.auth.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务调用效率分析
 * @author Evance
 * @version $Id: UserLoginLogAspect.java, v 0.1 2018年4月15日 上午12:34:08 Evance Exp $
 */
@Component
@Aspect
public class UserServiceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceLogAspect.class);

    /**
    * 定义一个切入点.
    * 解释下：
    * ~ 第一个 * 代表任意修饰符及任意返回值.
    * ~ 第二个 * 定义在web包或者子包
    * ~ 第三个 * 任意方法
    * ~ .. 匹配任意数量的参数.
    */
    @Pointcut("execution(* cn.evake.auth.service.user.UserService.*(..))  && !execution(* cn.evake.auth.service.user.UserService.get*(..))")
    public void userLogPointcut() {

    }

    @org.aspectj.lang.annotation.Around("userLogPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.debug("excute  userService service :{} has spends :{} ms", joinPoint
                .getSignature().getName(), (end - start));
            return result;
        } catch (Throwable e) {
            throw e;
        }

    }

}