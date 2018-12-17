/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.usermodel;

import java.awt.image.BufferedImage;

/**
 * 验证码
 * @author Evance
 * @version $Id: AuthCodeModel.java, v 0.1 2018年3月13日 下午11:19:30 Evance Exp $
 */
public class AuthCodeModel {

    private String        code;

    private BufferedImage codePic;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BufferedImage getCodePic() {
        return codePic;
    }

    public void setCodePic(BufferedImage codePic) {
        this.codePic = codePic;
    }

}
