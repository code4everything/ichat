package org.code4everything.ichat.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.ichat.dao.ReportDAO;
import org.code4everything.ichat.domain.Report;
import org.code4everything.ichat.model.ReportDTO;
import org.code4everything.ichat.service.CommonService;
import org.code4everything.ichat.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018/8/30
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportDAO reportDAO;

    private final CommonService commonService;

    @Autowired
    public ReportServiceImpl(ReportDAO reportDAO, CommonService commonService) {
        this.reportDAO = reportDAO;
        this.commonService = commonService;
    }

    @Override
    public Report save(String userId, ReportDTO reportDTO) {
        Report report = new Report(reportDTO);
        report.setCreateTime(System.currentTimeMillis());
        report.setId(RandomUtil.simpleUUID());
        report.setIsHandled(false);
        report.setUserId(userId);
        reportDAO.save(report);
        return report;
    }
}
