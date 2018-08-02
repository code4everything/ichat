package org.code4everything.ichat.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.ichat.model.PasswordResetDTO;
import org.code4everything.ichat.model.RegisterDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018-07-31
 */
@RestController
@Api(description = "用户操作接口")
public class UserController {

    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    @ApiOperation("重置密码")
    public ResultObject resetPassword(@RequestBody @ApiParam PasswordResetDTO password) {
        return new ResultObject();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation("用户注册")
    public ResultObject register(@RequestBody @ApiParam RegisterDTO register) {
        return new ResultObject();
    }
}
