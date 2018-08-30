package org.code4everything.ichat.web;

import com.zhazhapan.util.Checker;
import com.zhazhapan.util.model.CheckResult;
import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.model.ReportDTO;
import org.code4everything.ichat.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/8/30
 */
@RestController
@RequestMapping("/report")
@Api(value = "/report", description = "反馈相关接口")
public class ReportController {

    private final HttpServletRequest request;
    
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService, HttpServletRequest request) {
        this.reportService = reportService;
        this.request = request;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation("保存举报信息")
    public ResultObject save(@RequestBody @ApiParam ReportDTO report) {
        CheckResult result = Checker.checkBean(report);
        if (result.passed) {
            String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
            result.resultObject = new ResultObject(reportService.save(userId, report));
        }
        return result.resultObject;
    }
}
