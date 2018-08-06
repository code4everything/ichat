package org.code4everything.ichat.factory;

import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import org.code4everything.ichat.constant.MessageConsts;

/**
 * @author pantao
 * @since 2018-08-02
 */
public class ResultFactory {

    private static ResultObject emailExistsResult = CheckResult.getErrorResult(401, MessageConsts.EMAIL_EXISTS);

    private static ResultObject emailErrorResult = CheckResult.getErrorResult(402, MessageConsts.EMAIL_ERROR);

    private static ResultObject loginErrorResult = CheckResult.getErrorResult(403, MessageConsts.LOGIN_ERROR);

    private static ResultObject emailNotRegisterResult = CheckResult.getErrorResult(404,
            MessageConsts.EMAIL_NOT_REGISTER);

    private static ResultObject codeExpiredResult = CheckResult.getErrorResult(405, MessageConsts.CODE_EXPIRED);

    private static ResultObject codeErrorResult = CheckResult.getErrorResult(406, MessageConsts.CODE_ERROR);

    private static ResultObject unauthorizedResult = CheckResult.getErrorResult(409, MessageConsts.PLEASE_LOGIN);

    public static ResultObject getUnauthorizedResult() {
        return unauthorizedResult;
    }

    public static ResultObject getCodeErrorResult() {
        return codeErrorResult;
    }

    public static ResultObject getCodeExpiredResult() {
        return codeExpiredResult;
    }

    public static ResultObject getEmailNotRegisterResult() {
        return emailNotRegisterResult;
    }

    public static ResultObject getLoginErrorResult() {
        return loginErrorResult;
    }

    public static ResultObject getEmailExistsResult() {
        return emailExistsResult;
    }

    public static ResultObject getEmailErrorResult() {
        return emailErrorResult;
    }
}
