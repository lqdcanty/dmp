/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.service.cert;

import com.efida.sports.cert.dao.entity.ScoreCertEntity;
import com.efida.sports.cert.dao.entity.ScoreCertPicEntity;
import com.efida.sports.cert.model.PagingResult;

/**
 * 
 * @author Evance
 * @version $Id: ScoreCertServcie.java, v 0.1 2018年8月7日 上午1:03:45 Evance Exp $
 */
public interface ScoreCertServcie {

    /**
     * 
     * 获取需要生成图片的数据
     * @return
     */
    public PagingResult<ScoreCertEntity> getNeedGenPic(int page, int size);

    /**
     * 更新证书数据
     * @param certSn
     * @return 
     */
    public ScoreCertPicEntity upSynScoreCertByCertNo(String certSn, String imgUrl);

    /**
     * 获取证书信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param certSn
     * @return
     */
    public ScoreCertEntity getCertBySn(String certSn);

    /**
     * 获取当前数据并加锁
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param id
     * @return
     */
    public ScoreCertEntity getCertByIdForUpdate(long id);

}
