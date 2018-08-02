package org.code4everything.ichat.web;

import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.RandomUtils;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.code4everything.ichat.constant.MessageConsts;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018-08-01
 */
@RestController
@Api(description = "公共接口")
public class CommonController {

    private final UserService userService;

    private final CommonService commonService;

    private final HttpServletRequest request;

    private static final Logger logger = Logger.getLogger(CommonController.class);

    @Autowired
    public CommonController(UserService userService, CommonService commonService, HttpServletRequest request) {
        this.userService = userService;
        this.commonService = commonService;
        this.request = request;
    }

    @RequestMapping(value = "/common/code", method = RequestMethod.POST)
    @ApiOperation("发送验证码")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", value = "邮箱", dataType = "string", required = true),
            @ApiImplicitParam(name = "method", value = "发送方式：1注册，2重置密码，3修改邮箱", dataType = "int", required = true)})
    public ResultObject sendCode(@RequestParam String email, @RequestParam Integer method) {
        ResultObject resultObject;
        if (Checker.isEmail(email) && Checker.isNotNull(method)) {
            boolean result = true;
            // 注册、重置密码：需验证邮箱是否存在
            if (method == 1 || method == ValueConsts.THREE_INT) {
                result = !userService.emailExists(email);
            }
            if (result) {
                // 当邮件不存在或是其他 method 时才发送验证码
                String code = String.valueOf(method + RandomUtils.getRandomInteger(6));
                commonService.saveCode(request.getSession().getId() + ".code", code);
                commonService.sendCode(email, code);
                resultObject = new ResultObject(MessageConsts.SEND_CODE_SUCCESS);
            } else {
                resultObject = CheckResult.getErrorResult(MessageConsts.EMAIL_EXISTS);
            }
        } else {
            resultObject = CheckResult.getErrorResult();
        }
        return resultObject;
    }
}
