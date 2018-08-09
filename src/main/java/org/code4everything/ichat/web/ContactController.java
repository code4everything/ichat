package org.code4everything.ichat.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.model.ContactDTO;
import org.code4everything.ichat.service.BlackListService;
import org.code4everything.ichat.service.ContactService;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018-08-07
 */
@RestController
@RequestMapping("/user/contact")
@Api(value = "/user/contact ", description = "联系人相关操作")
public class ContactController {

    private final ContactService contactService;

    private final HttpServletRequest request;

    private final UserService userService;

    private final BlackListService blackListService;

    @Autowired
    public ContactController(ContactService contactService, HttpServletRequest request, UserService userService,
                             BlackListService blackListService) {
        this.contactService = contactService;
        this.request = request;
        this.userService = userService;
        this.blackListService = blackListService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation("获取好友列表")
    public ResultObject list() {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        return new ResultObject(contactService.list(userId));
    }

    @RequestMapping(value = "/agree", method = RequestMethod.PUT)
    @ApiOperation("同意好友邀请")
    @ApiImplicitParam(name = "friendId", value = "好友编号", dataType = "string", required = true)
    public ResultObject agree(@RequestParam String friendId) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        contactService.updateStatus(userId, friendId, IchatValueConsts.TWO_STR);
        return new ResultObject();
    }

    @RequestMapping(value = "/reject", method = RequestMethod.PUT)
    @ApiOperation("拒绝好友邀请")
    public ResultObject reject(@RequestParam String friendId) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        contactService.updateStatus(userId, friendId, IchatValueConsts.ZERO_STR);
        return new ResultObject();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation("更新标签和备注名")
    public ResultObject update(@RequestBody @ApiParam ContactDTO contact) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        CheckResult result = Checker.checkBean(contact);
        if (result.passed) {
            contactService.update(userId, contact);
            return new ResultObject("更新成功");
        }
        return result.resultObject;
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ApiOperation("删除联系人")
    public ResultObject remove(String id, String friendId) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        contactService.remove(id, userId, friendId);
        return new ResultObject("删除成功");
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ApiOperation("添加联系人")
    @ApiImplicitParam(name = "uid", value = "用户唯一标识符", dataType = "string", required = true)
    public ResultObject addContact(@RequestParam String uid) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        String friendId = userService.getUserIdByUid(uid);
        if (contactService.isFriend(userId, friendId)) {
            return new ResultObject("你们已经是好友关系，无需再添加");
        }
        if (contactService.isInvited(userId, friendId)) {
            return new ResultObject("已经发出了添加邀请，等待同意中");
        }
        if (blackListService.isBanned(userId, friendId)) {
            return new ResultObject("添加失败，对方在你黑名单中或你在对方黑名单列表中");
        }
        contactService.inviting(userId, friendId);
        return new ResultObject("发出邀请成功");
    }
}
