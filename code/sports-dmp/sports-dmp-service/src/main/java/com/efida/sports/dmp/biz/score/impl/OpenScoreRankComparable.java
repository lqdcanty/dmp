/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.biz.score.impl;

import java.util.Comparator;

import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

/**
 * 
 * @author Lenovo
 * @version $Id: OpenScoreRankComparable.java, v 0.1 2018年8月13日 下午8:28:11 Lenovo Exp $
 */
public class OpenScoreRankComparable implements Comparator<OpenScoreRankEntity> {

    //分数越大成绩越好
    private boolean isBigGood = true;

    public boolean isBigGood() {
        return isBigGood;
    }

    public void setBigGood(boolean isBigGood) {
        this.isBigGood = isBigGood;
    }

    @Override
    public int compare(OpenScoreRankEntity o1, OpenScoreRankEntity o2) {

            
            int flag = 0;
            if(o1.getScore().doubleValue()> o2.getScore().doubleValue()){
                flag = -1;
            }else if(o1.getScore().doubleValue() < o2.getScore().doubleValue()){
                flag = 1;
            }else{
                flag = 0;
            }
            
            if (isBigGood) {
                return flag;
            }
            
            return -flag;
            
    }

}
