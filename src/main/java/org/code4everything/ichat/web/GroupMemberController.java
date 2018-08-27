package org.code4everything.ichat.web;

import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/8/22
 */
@RestController
@RequestMapping("/group/member")
@Api(value = "/group/member", description = "组成员相关接口")
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    private final HttpServletRequest request;

    @Autowired
    public GroupMemberController(GroupMemberService groupMemberService, HttpServletRequest request) {
        this.groupMemberService = groupMemberService;
        this.request = request;
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ApiOperation("申请加入群组")
    @ApiImplicitParam(name = "link", value = "组链接", required = true, dataType = "string")
    public ResultObject join(@RequestParam String link) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        return new ResultObject(groupMemberService.joinGroup(userId, link) ? "加入成功（私有群需等待管理员同意）" : "组链接不存在");
    }

    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    @ApiOperation("邀请用户加入")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "uid", value = "用户唯一标识符（手机号，邮箱，编号，唯一标识符）", required = true, dataType = "string")})
    public ResultObject invite(@RequestParam String groupId, @RequestParam String uid) {
        String myId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        return new ResultObject(groupMemberService.inviteMember(myId, groupId, uid) ? "操作失败" : "邀请成功");
    }

    @RequestMapping(value = "/agree", method = RequestMethod.PUT)
    @ApiOperation("同意加入")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "uid", value = "用户唯一标识符（手机号，邮箱，编号，唯一标识符）", dataType = "string")})
    public ResultObject agree(@RequestParam String groupId, @RequestParam String uid) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        boolean result = groupMemberService.agree(userId, uid, groupId);
        return new ResultObject(result ? "加入成功" : "操作失败");
    }

    @RequestMapping(value = "/admin-mode", method = RequestMethod.PUT)
    @ApiOperation("切换成员管理员身份")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "群组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "isAdmin", value = "是否为管理员", allowableValues = "0 ,1", required = true,
                    dataType = "string")})
    public ResultObject toggleAdmin(@RequestParam String groupId, @RequestParam String userId,
                                    @RequestParam String isAdmin) {
        String myId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        if (myId.equals(userId) || !groupMemberService.isCreator(myId, groupId)) {
            return CheckResult.getErrorResult("权限不够");
        }
        groupMemberService.toggleAdminMode(groupId, userId, isAdmin);
        return new ResultObject("设置成功");
    }

    @RequestMapping(value = "/restricted", method = RequestMethod.PUT)
    @ApiOperation("设置成员是否受限")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "群组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "restricted", value = "是否限制成员发言", allowableValues = "0 ,1", required = true,
                    dataType = "string")})
    public ResultObject setRestricted(@RequestParam String groupId, @RequestParam String userId,
                                      @RequestParam String restricted) {
        String myId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        boolean result = groupMemberService.setRestricted(myId, groupId, userId, restricted);
        return result ? new ResultObject("设置成功") : CheckResult.getErrorResult("权限不够");
    }

    @RequestMapping(value = "/banned", method = RequestMethod.PUT)
    @ApiOperation("是否拉黑成员")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "群组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "userId", value = "用户编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "banned", value = "是否拉黑", allowableValues = "0, 1", required = true, dataType =
                    "string")})
    public ResultObject setBanned(@RequestParam String groupId, @RequestParam String userId,
                                  @RequestParam String banned) {
        String myId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        boolean result = groupMemberService.setBanned(myId, groupId, userId, banned);
        return result ? new ResultObject("设置成功") : CheckResult.getErrorResult("权限不够");
    }

    @RequestMapping(value = "/notification", method = RequestMethod.PUT)
    @ApiOperation("消息是否通知")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "群组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "notification", value = "是否允许通知", allowableValues = "0, 1", required = true,
                    dataType = "string")})
    public ResultObject setNotification(@RequestParam String groupId, @RequestParam String notification) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        groupMemberService.setNotification(groupId, userId, notification);
        return new ResultObject();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation("获取所有群组成员")
    public ResultObject listByGroupId(String groupId) {
        return new ResultObject(groupMemberService.listGroupMember(groupId));
    }
}
