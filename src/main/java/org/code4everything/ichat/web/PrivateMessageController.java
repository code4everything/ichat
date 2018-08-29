package org.code4everything.ichat.web;

import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/8/29
 */
@RestController
@RequestMapping("/user/message")
@Api(value = "/user/message", description = "私聊消息相关操作")
public class PrivateMessageController {

    private final PrivateMessageService privateMessageService;

    private final HttpServletRequest request;

    @Autowired
    public PrivateMessageController(PrivateMessageService privateMessageService, HttpServletRequest request) {
        this.privateMessageService = privateMessageService;
        this.request = request;
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation("删除（撤回）消息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "消息编号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "string")})
    public ResultObject removeMessage(@RequestParam String id, @RequestParam String userId) {
        String myId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        boolean result = privateMessageService.removeMessage(id, myId, userId);
        return result ? new ResultObject("删除成功") : CheckResult.getErrorResult("权限不够");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation("获取消息")
    @ApiImplicitParams({@ApiImplicitParam(name = "offset", value = "偏移量", dataType = "int"), @ApiImplicitParam(name =
            "userId", value = "用户编号", dataType = "string", required = true)})
    public ResultObject listMessage(@RequestParam(defaultValue = "0") Integer offset, @RequestParam String userId) {
        String myId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        return new ResultObject(privateMessageService.listByUserId(offset, myId, userId));
    }

}
