/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.dmp.service.dubbo.cover;

import cn.evake.auth.usermodel.PagingResult;
import com.efida.sport.admin.facade.model.page.SportsAdminPageResult;
import com.efida.sport.admin.facade.result.SportAdminResult;
import org.springframework.beans.BeanUtils;


/**
 * @author antony
 * @version $Id: CommonCover.java, v 0.1 2018年7月22日 下午4:05:20 antony Exp $
 * @desc
 */
public class PagingResultCover {

    public static PagingResult convertVO(com.efida.sport.facade.model.PagingResult source) {
        PagingResult target = new PagingResult();
        if (null != source) {
            BeanUtils.copyProperties(source, target);
        }
        return target;
    }

    public static PagingResult convertVO2(SportsAdminPageResult source) {
        PagingResult target = new PagingResult();
        if (null != source) {
            BeanUtils.copyProperties(source, target);
        }

        return target;
    }


}
