package com.efida.sports.dmp.web.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.efida.sports.dmp.dao.entity.OpenUnitEntity;

import cn.evake.auth.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "承办方账号")
public class UnitVo {

    private Long    id;

    @ApiModelProperty(value = "承办方编号")
    private String  unitCode;

    @ApiModelProperty(value = "承办方名称")
    private String  unitName;

    @ApiModelProperty(value = "承办方联系人")
    private String  contact;

    @ApiModelProperty(value = "承办方联系电")
    private String  phone;

    @ApiModelProperty(value = "承办邮箱")
    private String  email;

    @ApiModelProperty(value = "承办联系地址")
    private String  address;

    @ApiModelProperty(value = "承办方秘钥")
    private String  clientSecrect;

    @ApiModelProperty(value = "承办方账户状态")
    private Byte    isLock;

    @ApiModelProperty(value = "承办方白名单")
    private String  whiteIp;

    @ApiModelProperty(value = "承办方每分钟最大调用次数")
    private Integer maxPerMinute;

    @ApiModelProperty(value = "承办方备注")
    private String  remark;

    @ApiModelProperty(value = "承办方管理密码")
    private String  managerPass;

    @ApiModelProperty(value = "数据创建时间")
    private String  gmtCreateStr;

    @ApiModelProperty(value = "是不是top数据")
    private boolean isTop = false;

    public static UnitVo coverVo(OpenUnitEntity openEntity) {
        if (openEntity == null) {
            return null;
        }
        UnitVo openUnitVo = new UnitVo();
        BeanUtils.copyProperties(openEntity, openUnitVo);
        openUnitVo.setGmtCreateStr(DateUtil.formatDate(openEntity.getGmtCreate()));
        return openUnitVo;
    }

    public static UnitVo coverVo(OpenUnitEntity openEntity, List<String> unitCodes) {
        if (openEntity == null) {
            return null;
        }
        UnitVo openUnitVo = new UnitVo();
        BeanUtils.copyProperties(openEntity, openUnitVo);
        openUnitVo.setGmtCreateStr(DateUtil.formatDate(openEntity.getGmtCreate()));
        if (unitCodes != null && unitCodes.size() > 0) {
            openUnitVo.setTop(unitCodes.contains(openEntity.getUnitCode()));

        }
        return openUnitVo;
    }

    public static List<UnitVo> coverVos(List<OpenUnitEntity> openEntitys) {
        List<UnitVo> vos = new ArrayList<UnitVo>();
        if (CollectionUtils.isEmpty(openEntitys)) {
            return null;
        }
        for (OpenUnitEntity openUnitEntity : openEntitys) {
            vos.add(coverVo(openUnitEntity));
        }
        return vos;
    }

    public static List<UnitVo> coverVos(List<OpenUnitEntity> openEntitys, List<String> unitCodes) {
        List<UnitVo> vos = new ArrayList<UnitVo>();
        if (CollectionUtils.isEmpty(openEntitys)) {
            return null;
        }
        for (OpenUnitEntity openUnitEntity : openEntitys) {
            vos.add(coverVo(openUnitEntity, unitCodes));
        }
        return vos;
    }

    public static OpenUnitEntity toEntity(UnitVo openUnit) {
        if (openUnit == null) {
            return null;
        }
        OpenUnitEntity openUnitEntity = new OpenUnitEntity();
        BeanUtils.copyProperties(openUnit, openUnitEntity);
        return openUnitEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClientSecrect() {
        return clientSecrect;
    }

    public void setClientSecrect(String clientSecrect) {
        this.clientSecrect = clientSecrect;
    }

    public Byte getIsLock() {
        return isLock;
    }

    public void setIsLock(Byte isLock) {
        this.isLock = isLock;
    }

    public String getWhiteIp() {
        return whiteIp;
    }

    public void setWhiteIp(String whiteIp) {
        this.whiteIp = whiteIp;
    }

    public Integer getMaxPerMinute() {
        return maxPerMinute;
    }

    public void setMaxPerMinute(Integer maxPerMinute) {
        this.maxPerMinute = maxPerMinute;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getManagerPass() {
        return managerPass;
    }

    public void setManagerPass(String managerPass) {
        this.managerPass = managerPass;
    }

    public String getGmtCreateStr() {
        return gmtCreateStr;
    }

    public void setGmtCreateStr(String gmtCreateStr) {
        this.gmtCreateStr = gmtCreateStr;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean isTop) {
        this.isTop = isTop;
    }

}