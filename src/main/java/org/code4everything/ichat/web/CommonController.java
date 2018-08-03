package org.code4everything.ichat.web;

import cn.hutool.core.util.StrUtil;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.RandomUtils;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.code4everything.ichat.constant.MessageConsts;
import org.code4everything.ichat.factory.ResultFactory;
import org.code4everything.ichat.model.VerifyCodeDTO;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResultObject sendCode(@RequestBody @ApiParam VerifyCodeDTO code) {
        CheckResult checkResult = Checker.checkBean(code);
        ResultObject resultObject = checkResult.resultObject;
        if (checkResult.passed) {
            if (Checker.isEmail(code.getEmail())) {
                boolean result = true;
                // 注册、重置密码：需验证邮箱是否存在
                if (code.getMethod() == 1 || code.getMethod() == ValueConsts.THREE_INT) {
                    result = !userService.emailExists(code.getEmail());
                }
                if (result) {
                    // 当邮件不存在或是其他 method 时才发送验证码
                    int verifyCode = RandomUtils.getRandomInteger(6);
                    String sessionId = request.getSession().getId();
                    request.getSession().setAttribute("email", code.getEmail());
                    // 保存原始的验证码
                    commonService.saveCode(sessionId + "-code", String.valueOf(verifyCode));
                    String codeStr = String.valueOf(code.getMethod() + verifyCode);
                    // 开发阶段暂不开启邮件发送功能
                    // commonService.sendCode(code.getEmail(), codeStr);
                    logger.info(StrUtil.format("send verify code[{}] for " + sessionId, codeStr));
                    resultObject = new ResultObject(MessageConsts.SEND_CODE_SUCCESS);
                } else {
                    resultObject = ResultFactory.getEmailExistsResult();
                }
            } else {
                resultObject = ResultFactory.getEmailErrorResult();
            }
        }
        return resultObject;
    }
}
