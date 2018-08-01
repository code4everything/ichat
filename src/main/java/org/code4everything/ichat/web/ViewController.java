package org.code4everything.ichat.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author pantao
 * @since 2018/1/25
 */
@Controller
public class ViewController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin() {
        return "/signin";
    }
}
