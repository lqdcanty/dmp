/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.cover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.efida.sport.admin.facade.model.open.EventTypeModel;
import com.efida.sport.admin.facade.model.open.FieldTypeModel;
import com.efida.sport.admin.facade.model.open.GroupTypeModel;
import com.efida.sport.dmp.facade.model.ComelationRelationModel;

/**
 * 
 * @author zoutao
 * @version $Id: CompetitionCover.java, v 0.1 2018年7月28日 下午1:53:54 zoutao Exp $
 */
public class CompetitionRelationCover {

    public static List<ComelationRelationModel> getRelationsBySites(String sites,
                                                                    List<FieldTypeModel> fieldList) {

        ArrayList<ComelationRelationModel> list = new ArrayList<ComelationRelationModel>();
        Set<String> siteSet = new HashSet<String>();
        for (FieldTypeModel model : fieldList) {
            if (siteSet.contains(model.getFieldCode())) {
                continue;
            }
            if (StringUtils.isNotBlank(sites)) {
                List<String> siteCodes = JSONObject.parseArray(sites, String.class);//把字符串转换成集合
                if (siteCodes.contains(model.getFieldCode())) {
                    siteSet.add(model.getFieldCode());
                    ComelationRelationModel relation = new ComelationRelationModel();
                    relation.setCode(model.getFieldCode());
                    relation.setName(model.getFieldName());
                    list.add(relation);
                }
            }
        }

        return list;
    }

    public static List<ComelationRelationModel> getRelationsByGroups(String groups,
                                                                     List<GroupTypeModel> groupList) {
        ArrayList<ComelationRelationModel> list = new ArrayList<ComelationRelationModel>();
        Set<String> groupSet = new HashSet<String>();
        for (GroupTypeModel model : groupList) {
            if (groupSet.contains(model.getGroupCode())) {
                continue;
            }
            if (StringUtils.isNotBlank(groups)) {
                List<String> groupCodes = JSONObject.parseArray(groups, String.class);//把字符串转换成集合
                if (groupCodes.contains(model.getGroupCode())) {
                    groupSet.add(model.getFieldCode());
                    ComelationRelationModel relation = new ComelationRelationModel();
                    relation.setCode(model.getGroupCode());
                    relation.setName(model.getGroupName());
                    list.add(relation);
                }
            }

        }

        return list;
    }

    public static List<ComelationRelationModel> getRelationsByEvents(String events,
                                                                     List<EventTypeModel> eventList) {
        ArrayList<ComelationRelationModel> list = new ArrayList<ComelationRelationModel>();
        Set<String> eventSet = new HashSet<String>();
        for (EventTypeModel model : eventList) {
            if (eventSet.contains(model.getEventCode())) {
                continue;
            }
            if (StringUtils.isNotBlank(events)) {
                List<String> eventCodes = JSONObject.parseArray(events, String.class);//把字符串转换成集合
                if (eventCodes.contains(model.getEventCode())) {
                    eventSet.add(model.getEventCode());
                    ComelationRelationModel relation = new ComelationRelationModel();
                    relation.setCode(model.getEventCode());
                    relation.setName(model.getEventName());
                    list.add(relation);
                }
            }
        }
        return list;
    }

}
