package org.code4everything.ichat.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.model.GroupDTO;
import org.code4everything.ichat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/8/13
 */
@RestController
@RequestMapping("/group")
@Api(value = "/group", description = "群组功能相关接口")
public class GroupController {

    private final GroupService groupService;

    private final HttpServletRequest request;

    @Autowired
    public GroupController(GroupService groupService, HttpServletRequest request) {
        this.groupService = groupService;
        this.request = request;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ApiOperation("新建分组")
    public ResultObject newGroup(@RequestBody @ApiParam GroupDTO group) {
        CheckResult result = Checker.checkBean(group);
        if (result.passed) {
            String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
            return new ResultObject(groupService.newGroup(userId, group));
        }
        return result.resultObject;
    }

    @RequestMapping(value = "/dismiss", method = RequestMethod.DELETE)
    @ApiOperation("解散分组")
    @ApiImplicitParam(name = "groupId", value = "群组编号", required = true, dataType = "string")
    public ResultObject leave(@RequestParam String groupId) {
        groupService.dismiss(request.getSession().getAttribute(IchatValueConsts.ID_STR).toString(), groupId);
        return new ResultObject("解散群组成功");
    }

    @RequestMapping(value = "/avatar", method = RequestMethod.PUT)
    @ApiOperation("更新组头像")
    public ResultObject uploadAvatar(@RequestParam String groupId, @RequestParam MultipartFile avatar) {
        ResultObject resultObject = new ResultObject();
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        String url = groupService.updateAvatar(userId, groupId, avatar);
        if (url.startsWith(IchatValueConsts.AVATAR_MAPPING)) {
            // 更新头像成功
            resultObject.data = url;
        } else {
            // 上传头像失败
            resultObject.code = 407;
            resultObject.message = url;
        }
        return resultObject;

    }
}
