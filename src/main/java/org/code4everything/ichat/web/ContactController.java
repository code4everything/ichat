package org.code4everything.ichat.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.service.ContactService;
import org.code4everything.ichat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018-08-07
 */
@RestController
@RequestMapping("/user/contact")
@Api("联系人相关操作")
public class ContactController {

    private final ContactService contactService;

    private final HttpServletRequest request;

    private final UserService userService;

    @Autowired
    public ContactController(ContactService contactService, HttpServletRequest request, UserService userService) {
        this.contactService = contactService;
        this.request = request;
        this.userService = userService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ApiOperation("添加联系人")
    @ApiImplicitParam(name = "uid", value = "用户唯一标识符", dataType = "string", required = true)
    public ResultObject addContact(@RequestParam String uid) {
        String inviter = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        String invitee = userService.getUserIdByUid(uid);
        if (contactService.isFriend(inviter, invitee)) {
            return new ResultObject("你们已经是好友关系，无需再添加");
        }
        if (contactService.isInviting(inviter, invitee)) {
            return new ResultObject("已经发出了添加邀请，等待同意中");
        }
        contactService.save(inviter, invitee);
        return new ResultObject("发出邀请成功");
    }
}
