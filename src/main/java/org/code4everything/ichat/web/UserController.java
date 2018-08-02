package org.code4everything.ichat.web;

import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.Formatter;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.ichat.constant.MessageConsts;
import org.code4everything.ichat.domain.User;
import org.code4everything.ichat.factory.ResultFactory;
import org.code4everything.ichat.model.LoginDTO;
import org.code4everything.ichat.model.PasswordResetDTO;
import org.code4everything.ichat.model.RegisterDTO;
import org.code4everything.ichat.model.UserVO;
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
 * @since 2018-07-31
 */
@RestController
@Api(description = "用户操作接口")
public class UserController {

    private final UserService userService;

    private final HttpServletRequest request;

    private final CommonService commonService;

    @Autowired
    public UserController(UserService userService, HttpServletRequest request, CommonService commonService) {
        this.userService = userService;
        this.request = request;
        this.commonService = commonService;
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
    @ApiOperation("重置密码")
    public ResultObject resetPassword(@RequestBody @ApiParam PasswordResetDTO password) {
        CheckResult result = Checker.checkBean(password);
        ResultObject resultObject = result.resultObject;
        if (result.passed) {
            if (Checker.isEmail(password.getEmail())) {
                // 判断邮箱是否已经注册
                boolean exists = userService.emailExists(password.getEmail());
                if (exists) {
                    String codeKey = request.getSession().getId() + "-code";
                    // 获取原始的验证码
                    int code = Formatter.stringToInt(commonService.getByRedisWithoutMongo(codeKey));
                    if (code < 0) {
                        resultObject = ResultFactory.getCodeExpiredResult();
                    } else if (code - ValueConsts.TWO_INT == password.getCode()) {
                        // 重置密码
                        userService.resetPassword(password.getEmail(), password.getPassword());
                    } else {
                        resultObject = ResultFactory.getCodeErrorResult();
                    }
                } else {
                    resultObject = ResultFactory.getEmailNotRegisterResult();
                }
            } else {
                resultObject = ResultFactory.getEmailErrorResult();
            }
        }
        return resultObject;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation("用户注册")
    public ResultObject register(@RequestBody @ApiParam RegisterDTO register) {
        CheckResult result = Checker.checkBean(register);
        ResultObject resultObject = result.resultObject;
        if (result.passed) {
            if (Checker.isEmail(register.getEmail())) {
                boolean consistent = register.getEmail().equals(request.getSession().getAttribute("email"));
                resultObject = ResultFactory.getEmailExistsResult();
                if (consistent) {
                    String codeKey = request.getSession().getId() + "-code";
                    // 获取原始的验证码
                    int code = Formatter.stringToInt(commonService.getByRedisWithoutMongo(codeKey));
                    if (code < 0) {
                        resultObject = ResultFactory.getCodeExpiredResult();
                    } else if (code - ValueConsts.ONE_INT == register.getCode()) {
                        // 保存用户信息
                        boolean emailExists = userService.emailExists(register.getEmail());
                        if (!emailExists) {
                            userService.saveUser(new User(register));
                            resultObject = new ResultObject(MessageConsts.REGISTER_SUCCESS);
                        }
                    } else {
                        resultObject = ResultFactory.getCodeErrorResult();
                    }
                }
            } else {
                resultObject = ResultFactory.getEmailErrorResult();
            }
        }
        return resultObject;
    }

    @RequestMapping(value = "/login", method = RequestMethod.PUT)
    @ApiOperation("用户登录")
    public ResultObject login(@RequestBody @ApiParam LoginDTO login) {
        CheckResult result = Checker.checkBean(login);
        ResultObject resultObject = result.resultObject;
        if (result.passed) {
            if (Checker.isEmail(login.getEmail())) {
                // 登录
                User user = userService.login(login.getEmail(), login.getPassword());
                if (Checker.isNull(user)) {
                    resultObject = ResultFactory.getLoginErrorResult();
                } else {
                    // 登录成功
                    request.getSession().setAttribute(ValueConsts.USER_STRING, user);
                    resultObject.data = new UserVO(user);
                }
            } else {
                resultObject = ResultFactory.getEmailErrorResult();
            }
        }
        return resultObject;
    }
}
