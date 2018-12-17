package com.efida.sports.dmp.synch.data.common.dao.entity;

/**
 * 赛场信息
 * @author wang yi
 * @desc 
 * @version $Id: SiteModel.java, v 0.1 2018年9月14日 下午5:09:33 wang yi Exp $
 */
public class EventModel {

    /**
     * 对方ID
     */
    private String reEventid;

    /**
     * 编号(官方)
     */
    private String eventCode;

    /**
     * 名称(官方)
     */
    private String eventName;

    public String getReEventid() {
        return reEventid;
    }

    public void setReEventid(String reEventid) {
        this.reEventid = reEventid;
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
