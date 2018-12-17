package com.efida.sports.dmp.dao.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.efida.sports.dmp.enums.ApplyStatisticsTypeEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author zoutao
 * @since 2018-09-13
 */
public class ApplyStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long              id;
    /**
     * 类型(apply_once:报名一次,apply_many:报名多次,apply_many_event:报名多个项目)
     */
    private String            type;
    /**
     * 数量
     */
    private Long              quantity;

    @TableField(exist = false)
    private String            typeDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getTypeDesc() {
        if (StringUtils.isNotBlank(this.getType())) {
            return ApplyStatisticsTypeEnum.getDescByCode(this.getType());
        }
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Override
    public String toString() {
        return "ApplyStatistics [id=" + id + ", type=" + type + ", quantity=" + quantity
               + ", typeDesc=" + typeDesc + "]";
    }

}
