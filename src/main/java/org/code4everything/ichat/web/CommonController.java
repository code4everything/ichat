package org.code4everything.ichat.web;

import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018-08-01
 */
@RestController
@Api(description = "公共接口")
public class CommonController {

    private ResultObject resultObject;

    private final UserService userService;

    private final CommonService commonService;

    private final Logger LOGGER = Logger.getLogger(CommonController.class);

    @Autowired
    public CommonController(UserService userService, CommonService commonService) {
        this.userService = userService;
        this.commonService = commonService;
    }

    @RequestMapping(value = "/common/code", method = RequestMethod.POST)
    @ApiOperation("发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", required = true),
            @ApiImplicitParam(name = "method", value = "发送方式：1注册，2重置密码，3修改邮箱", dataType = "int", required = true)})
    public ResultObject sendCode(@RequestParam String email, @RequestParam Integer method) {
        if (Checker.isEmail(email) && Checker.isNotNull(method)) {
            boolean result = true;
            // 注册、重置密码：需验证邮箱是否存在
            if (method == 1 || method == ValueConsts.THREE_INT) {
                result = !userService.emailExists(email);
            }
            if (result) {
                result = commonService.sendCode(email, method);
                resultObject = result ? new ResultObject("邮件发送成功") : CheckResult.getErrorResult(501, "发送邮件失败");
            } else {
                resultObject = CheckResult.getErrorResult("邮箱已经存在");
            }
        } else {
            resultObject = CheckResult.getErrorResult();
        }
        return resultObject;
    }
}
