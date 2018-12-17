package com.efida.sports.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/cms/ueditor")
public class UeditorDemoController {

    private String udetorDemo = "/cms/udetor_demo";

    @RequestMapping("demo")
    public String queryProjectList(HttpServletRequest request) {
        return udetorDemo;
    }

}
