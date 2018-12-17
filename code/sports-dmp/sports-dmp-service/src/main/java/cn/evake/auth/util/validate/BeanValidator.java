package cn.evake.auth.util.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 对象验证器
 * @author wang yi
 * @desc 
 * @version $Id: BeanValidator.java, v 0.1 2018年11月9日 上午11:30:57 wang yi Exp $
 */
public class BeanValidator {

    /**
     * 验证某个bean的参数
     *
     * @param object 被校验的参数
     * @throws ValidationException 如果参数校验不成功则抛出此异常
     */
    public static <T> void validate(T object) {
        if (object == null) {
            throw new ValidationException("数据不能为空");
        }
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        //如果有验证信息，则取出来包装成异常返回
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return;
        }
        throw new ValidationException(convertErrorMsg(constraintViolations));
    }

    /**
     * 转换异常信息
     * @param set
     * @param <T>
     * @return
     */
    private static <T> String convertErrorMsg(Set<ConstraintViolation<T>> set) {
        for (ConstraintViolation<T> cv : set) {
            if (StringUtils.isNotBlank(cv.getMessage())) {
                return cv.getMessage();
            }
        }
        return null;
    }
}