/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.web.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.efida.sport.admin.facade.model.open.EventTypeModel;
import com.efida.sport.admin.facade.model.open.FieldTypeModel;
import com.efida.sport.admin.facade.model.open.GroupTypeModel;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author zoutao
 * @version $Id: ComelationRelationVo.java, v 0.1 2018年8月2日 下午6:33:19 zoutao Exp $
 */
public class ComelationRelationVo {

    @ApiModelProperty(value = "名称")
    private String  name;
    @ApiModelProperty(value = "编号")
    private String  code;
    @ApiModelProperty(value = "是否选择：true, 选中 false：未选中")
    private Boolean isChoose = false;
    @ApiModelProperty(value = "类型，项：event,组：group,赛场：site")
    private String  type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(Boolean isChoose) {
        this.isChoose = isChoose;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static List<ComelationRelationVo> getVosBySites(String sites,
                                                           List<FieldTypeModel> fieldList) {

        ArrayList<ComelationRelationVo> list = new ArrayList<ComelationRelationVo>();
        Set<String> siteSet = new HashSet<String>();
        for (FieldTypeModel model : fieldList) {
            if (siteSet.contains(model.getFieldCode())) {
                continue;
            }
            siteSet.add(model.getFieldCode());
            ComelationRelationVo vo = new ComelationRelationVo();
            vo.setType("site");
            vo.setCode(model.getFieldCode());
            vo.setName(model.getFieldName());
            if (StringUtils.isNotBlank(sites)) {
                List<String> siteCodes = JSONObject.parseArray(sites, String.class);//把字符串转换成集合
                vo.setIsChoose(siteCodes.contains(model.getFieldCode()));
            }
            list.add(vo);
        }

        return list;
    }

    public static List<ComelationRelationVo> getVosByGroups(String groups,
                                                            List<GroupTypeModel> groupList) {
        ArrayList<ComelationRelationVo> list = new ArrayList<ComelationRelationVo>();
        Set<String> groupSet = new HashSet<String>();
        for (GroupTypeModel model : groupList) {
            if (groupSet.contains(model.getGroupCode())) {
                continue;
            }
            groupSet.add(model.getGroupCode());
            ComelationRelationVo vo = new ComelationRelationVo();
            vo.setType("group");
            vo.setCode(model.getGroupCode());
            vo.setName(model.getGroupName());
            if (StringUtils.isNotBlank(groups)) {
                List<String> groupCodes = JSONObject.parseArray(groups, String.class);//把字符串转换成集合
                vo.setIsChoose(groupCodes.contains(model.getGroupCode()));
            }
            list.add(vo);
        }

        return list;
    }

    public static List<ComelationRelationVo> getVosByEvents(String events,
                                                            List<EventTypeModel> eventList) {
        ArrayList<ComelationRelationVo> list = new ArrayList<ComelationRelationVo>();
        Set<String> eventSet = new HashSet<String>();
        for (EventTypeModel model : eventList) {
            if (eventSet.contains(model.getEventCode())) {
                continue;
            }
            eventSet.add(model.getEventCode());
            ComelationRelationVo vo = new ComelationRelationVo();
            vo.setType("event");
            vo.setCode(model.getEventCode());
            vo.setName(model.getEventName());
            if (StringUtils.isNotBlank(events)) {
                List<String> eventCodes = JSONObject.parseArray(events, String.class);//把字符串转换成集合
                vo.setIsChoose(eventCodes.contains(model.getEventCode()));
            }
            list.add(vo);
        }

        return list;
    }

}
