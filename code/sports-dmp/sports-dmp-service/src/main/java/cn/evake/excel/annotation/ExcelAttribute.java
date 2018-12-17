package cn.evake.excel.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * excel反射对象注解
 * @author wang yi
 * @desc 
 * @version $Id: ExcelAttribute.java, v 0.1 2018年8月16日 上午10:43:04 wang yi Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface ExcelAttribute {

    /** 
     * Excel中的列名 
     *  
     * @return 
     */
    public abstract String name();

    /** 
     * 列名对应的A,B,C,D...,不指定按照默认顺序排序 
     *  
     * @return 
     */
    public abstract String column() default "";

    /** 
     * 提示信息 
     *  
     * @return 
     */
    public abstract String prompt() default "";

    /** 
     * 设置只能选择不能输入的列内容 
     *  
     * @return 
     */
    public abstract String[] combo() default {};

    /** 
     * 是否导出数据 
     *  
     * @return 
     */
    public abstract boolean isExport() default true;

    /** 
     * 是否为重要字段（整列标红,着重显示） 
     *  
     * @return 
     */
    public abstract boolean isMark() default false;

    /** 
     * 是否合计当前列 
     *  
     * @return 
     */
    public abstract boolean isSum() default false;
}