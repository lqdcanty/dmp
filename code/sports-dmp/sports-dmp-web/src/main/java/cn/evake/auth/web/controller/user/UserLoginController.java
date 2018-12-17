/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.web.controller.user;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;
import cn.evake.auth.usermodel.AuthCodeModel;
import cn.evake.auth.util.VerifyCodeUtil;

/**
 * 用户登录
 * @author Evance
 * @version $Id: UserController.java, v 0.1 2018年2月25日 下午9:34:13 Evance Exp $
 */
@Controller
@RequestMapping("sys")
@Authority
public class UserLoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 验证码获取
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param req
     * @param resp
     * @throws IOException
     */
    @Authority(AuthorityTypeEnum.NoAuthority)
    @RequestMapping(value = "authcode/yzm.action")
    public void findRandom(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuthCodeModel codeAndPic = VerifyCodeUtil.generateCodeAndPic();
        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute("code", codeAndPic.getCode());
        logger.info("生成的验证码为：{}", codeAndPic.getCode());
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -1);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中.
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(codeAndPic.getCodePic(), "jpeg", sos);
        sos.close();
    }
}
