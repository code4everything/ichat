package org.code4everything.ichat.web;

import com.zhazhapan.util.model.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018-07-31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final ResultObject resultObject;

    @Autowired
    public UserController(ResultObject resultObject) {this.resultObject = resultObject;}

    public ResultObject updatePhone(@RequestParam String phone) {
        return resultObject;
    }
}
