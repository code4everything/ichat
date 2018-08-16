package org.code4everything.ichat.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.model.GroupDTO;
import org.code4everything.ichat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            return new ResultObject(groupService.newGroup("ii", group));
        }
        return result.resultObject;
    }
}
