package cn.evake.auth.web.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.evake.auth.annotation.Authority;
import cn.evake.auth.annotation.AuthorityTypeEnum;

@Controller
public class IndexController {

    @RequestMapping("/")
    @Authority(value = AuthorityTypeEnum.NoAuthority)
    public ModelAndView index() {
        return new ModelAndView("main/login");
    }
}