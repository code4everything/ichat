package org.code4everything.ichat.service;

import org.code4everything.ichat.domain.Report;
import org.code4everything.ichat.model.ReportDTO;

import java.util.List;

/**
 * @author pantao
 * @since 2018/8/30
 */
public interface ReportService {

    List<Report> list(String userId);

    Report save(String userId, ReportDTO reportDTO);
}
