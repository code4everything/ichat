package org.code4everything.ichat.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
    public ResultObject invite(String groupId, String uid) {
        String myId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        return new ResultObject(groupMemberService.inviteMember(myId, groupId, uid) ? "操作失败" : "邀请成功");
    }
}
