package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018/8/30
 */
@Repository
public interface ReportDAO extends MongoRepository<Report, String> {

}
