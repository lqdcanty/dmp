package com.efida.sports.dmp.synch.data.common.dao.entity;

/**
 * 赛场信息
 * @author wang yi
 * @desc 
 * @version $Id: SiteModel.java, v 0.1 2018年9月14日 下午5:09:33 wang yi Exp $
 */
public class GroupModel {

    /**
     * 对方ID
     */
    private String reGroupid;

    /**
     * 编号(官方)
     */
    private String groupCode;

    /**
     * 名称(官方)
     */
    private String groupName;

    public String getReGroupid() {
        return reGroupid;
    }

    public void setReGroupid(String reGroupid) {
        this.reGroupid = reGroupid;
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

}
