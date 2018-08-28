package org.code4everything.ichat.web;

import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.service.GroupMemberService;
import org.code4everything.ichat.service.GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/8/28
 */
@RestController
@RequestMapping("/group/message")
@Api(value = "/group/message", description = "群组消息操作相关接口")
public class GroupMessageController {

    private final GroupMessageService groupMessageService;

    private final HttpServletRequest request;

    private final GroupMemberService groupMemberService;

    @Autowired
    public GroupMessageController(GroupMessageService groupMessageService, HttpServletRequest request,
                                  GroupMemberService groupMemberService) {
        this.groupMessageService = groupMessageService;
        this.request = request;
        this.groupMemberService = groupMemberService;
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation("删除（撤回）消息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "消息编号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "groupId", value = "群组编号", required = true, dataType = "string")})
    public ResultObject removeMessage(@RequestParam String id, @RequestParam String groupId) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        boolean result = groupMessageService.removeMessage(userId, id, groupId);
        return result ? new ResultObject("删除成功") : CheckResult.getErrorResult("权限不够");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation("获取消息")
    @ApiImplicitParams({@ApiImplicitParam(name = "offset", value = "偏移量", dataType = "int"), @ApiImplicitParam(name =
            "groupId", value = "群组编号", dataType = "string", required = true)})
    public ResultObject listMessage(@RequestParam(defaultValue = "0") Integer offset, @RequestParam String groupId) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        if (groupMemberService.isMember(groupId, userId)) {
            return new ResultObject(groupMessageService.listByGroupId(offset, groupId));
        }
        return CheckResult.getErrorResult("权限不够");
    }
}
