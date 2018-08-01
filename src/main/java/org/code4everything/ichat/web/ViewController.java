package org.code4everything.ichat.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author pantao
 * @since 2018/1/25
 */
@Controller
@Api(description = "视图页面映射")
public class ViewController {

    @ApiOperation("登录注册页面")
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin() {
        return "/signin";
    }
}
