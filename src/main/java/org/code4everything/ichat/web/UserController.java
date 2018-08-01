package org.code4everything.ichat.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018-07-31
 */
@RestController
@Api(description = "用户操作接口")
public class UserController {

    private final ResultObject resultObject;

    @Autowired
    public UserController(ResultObject resultObject) {this.resultObject = resultObject;}

    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    @ApiOperation("重置密码")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", dataType = "string", required = true),
            @ApiImplicitParam(name = "password", value = "新密码", dataType = "string", required = true)})
    public ResultObject resetPassword(@RequestParam String email, @RequestParam String code,
                                      @RequestParam String password) {
        return resultObject;
    }


}
