package com.efida.sports.dmp.web.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.efida.sport.admin.facade.model.open.EventTypeModel;
import com.efida.sport.admin.facade.model.open.FieldTypeModel;
import com.efida.sport.admin.facade.model.open.GroupTypeModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 比赛项Vo类
 * @author wang yi
 * @desc 
 * @version $Id: MatchItemVo.java, v 0.1 2018年7月18日 下午4:40:05 wang yi Exp $
 */
@ApiModel(value = "比赛项Model")
public class MatchItemVo {

    @ApiModelProperty(value = "项目编号")
    private String gameCode;

    @ApiModelProperty(value = "赛事编号")
    private String matchCode;

    @ApiModelProperty(value = "赛事名称")
    private String matchName;

    @ApiModelProperty(value = "分赛场编号")
    private String fieldCode;

    @ApiModelProperty(value = "分赛场名称")
    private String fieldName;

    @ApiModelProperty(value = "分组编号")
    private String groupCode;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "细类编号")
    private String eventCode;

    @ApiModelProperty(value = "细类名称")
    private String eventName;

    public static List<MatchItemVo> toItemVos(MatchDetailModel matchs) {
        List<MatchItemVo> results = new ArrayList<MatchItemVo>();
        if (matchs == null) {
            return results;
        }
        List<EventTypeModel> eventTypeList = matchs.getEventTypeList();
        List<GroupTypeModel> groupTypeList = matchs.getGroupTypeList();
        List<FieldTypeModel> fieldTypeList = matchs.getFieldTypeList();
        for (EventTypeModel eventTypeModel : eventTypeList) {
            MatchItemVo matchItemVo = new MatchItemVo();
            matchItemVo.setGameCode(matchs.getGameCode());
            matchItemVo.setEventCode(eventTypeModel.getEventCode());
            matchItemVo.setEventName(eventTypeModel.getEventName());
            matchItemVo.setGroupCode(eventTypeModel.getGroupCode());
            GroupTypeModel groupTypeModel = getGroupType(eventTypeModel.getGroupCode(),
                groupTypeList);
            if (groupTypeModel != null) {
                matchItemVo.setGroupName(groupTypeModel.getGroupName());
            }
            matchItemVo.setFieldCode(eventTypeModel.getFieldCode());
            FieldTypeModel fileTypeModel = getFieldType(eventTypeModel.getFieldCode(),
                fieldTypeList);
            if (fileTypeModel != null) {
                matchItemVo.setFieldName(fileTypeModel.getFieldName());
            }
            matchItemVo.setMatchCode(matchs.getMatchCode());
            matchItemVo.setMatchName(matchs.getMatchName());
            results.add(matchItemVo);
        }
        return results;
    }

    private static FieldTypeModel getFieldType(String fieldCode,
                                               List<FieldTypeModel> fieldTypeList) {
        if (StringUtils.isBlank(fieldCode) || CollectionUtils.isEmpty(fieldTypeList)) {
            return null;
        }
        for (FieldTypeModel field : fieldTypeList) {
            if (fieldCode.equals(field.getFieldCode())) {
                return field;
            }
        }
        return null;
    }

    private static GroupTypeModel getGroupType(String groupCode,
                                               List<GroupTypeModel> groupTypeList) {
        if (StringUtils.isBlank(groupCode) || CollectionUtils.isEmpty(groupTypeList)) {
            return null;
        }
        for (GroupTypeModel groupType : groupTypeList) {
            if (groupCode.equals(groupType.getGroupCode())) {
                return groupType;
            }
        }
        return null;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
