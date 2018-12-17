/**
 * efida.com.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.efida.sports.cert.component.pic;

import java.util.List;

import com.efida.sports.cert.model.CerpicInfoModel;

/**
 * 图片处理线程处理类
 * @author wang yi
 * @desc 
 * @version $Id: CertPicHandleTread.java, v 0.1 2018年8月5日 下午9:21:53 wang yi Exp $
 */

public class CertPicHandleTread {//implements Runnable 

    private List<CerpicInfoModel> handelCerPicInfoModel;

    public CertPicHandleTread(List<CerpicInfoModel> handelCerPicInfoModel) {
        super();
        this.handelCerPicInfoModel = handelCerPicInfoModel;
    }

    /*@Autowired
    private CertPicService cerpicService;

    @Override
    public void run() {
        for (CerpicInfoModel cerpicInfoModel : handelCerPicInfoModel) {
            System.err.println("生成图片中完成.....");
        }
    }*/

}
