package org.code4everything.ichat.web;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.*;
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
        if (groupService.linkExists(group.getLink())) {
            return CheckResult.getErrorResult("组链接已经存在");
        }
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
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像文件", required = true, dataType = "string")})
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

    @RequestMapping(value = "/transfer", method = RequestMethod.PUT)
    @ApiOperation("转让群组")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "组编号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "userId", value = "新群主编号", required = true, dataType = "string")})
    public ResultObject transfer(String groupId, String userId) {
        String originalUserId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        groupService.updateCreator(originalUserId, groupId, userId);
        return new ResultObject("转让成功");
    }

    @RequestMapping(value = "/type", method = RequestMethod.PUT)
    @ApiOperation("更新群组类型（是否公开）")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "群组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "type", value = "群组类型：0公开，1私有", dataType = "string", required = true,
                    allowableValues = "0, 1")})
    public ResultObject updateType(@RequestParam String groupId, @RequestParam String type) {
        if (!IchatValueConsts.GROUP_TYPE_STRING.contains(type)) {
            return CheckResult.getErrorResult("参数格式不正确");
        }
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        groupService.updateType(userId, groupId, type);
        return new ResultObject("更新成功");
    }

    @RequestMapping(value = "/link", method = RequestMethod.PUT)
    @ApiOperation("更新组链接")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "组编号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "newLink", value = "新的组链接", dataType = "string", required = true)})
    public ResultObject updateLink(@RequestParam String groupId, @RequestParam String newLink) {
        if (groupService.linkExists(newLink)) {
            return CheckResult.getErrorResult("组链接已经存在");
        }
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        groupService.updateLink(userId, groupId, newLink);
        return new ResultObject("更新成功");
    }

    @RequestMapping(value = "/info", method = RequestMethod.PUT)
    @ApiOperation("更新群组信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "groupId", value = "组编号", dataType = "string", required = true),
            @ApiImplicitParam(name = "name", value = "组名称", dataType = "string"), @ApiImplicitParam(name = "about",
            value = "组介绍", dataType = "string")})
    public ResultObject updateInfo(@RequestParam String groupId, String name, String about) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        groupService.updateInfo(userId, groupId, name, about);
        return new ResultObject("更新成功");
    }

    @RequestMapping(value = "/link/exists", method = RequestMethod.GET)
    @ApiOperation("检查组链接是否存在")
    @ApiImplicitParam(name = "link", value = "组链接", required = true, dataType = "string")
    public ResultObject linkExists(String link) {
        JSONObject object = new JSONObject();
        object.put("exists", groupService.linkExists(link));
        return new ResultObject(object);
    }
}
