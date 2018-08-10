package org.code4everything.ichat.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018-08-10
 */
@RestController
@RequestMapping("/user/black-list")
@Api("用户黑名单相关操作")
public class BlackListController {

    private final BlackListService blackListService;

    private final HttpServletRequest request;

    @Autowired
    public BlackListController(BlackListService blackListService, HttpServletRequest request) {
        this.blackListService = blackListService;
        this.request = request;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ApiOperation("将联系人加入黑名单")
    @ApiImplicitParam(name = "uid", value = "用户唯一标识符", dataType = "string", required = true)
    public ResultObject addToBlackList(@RequestParam String uid) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        blackListService.addToBlackList(userId, uid);
        return new ResultObject("拉入黑名单成功");
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.DELETE)
    @ApiOperation("拉出黑名单")
    @ApiImplicitParam(name = "id", value = "列表编号", dataType = "string", required = true)
    public ResultObject delete(@RequestParam String id) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        blackListService.delete(userId, id);
        return new ResultObject("删除成功");
    }
}
