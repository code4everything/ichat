package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.Logger;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author pantao
 * @since 2018-08-01
 */
public interface LoggerDAO extends MongoRepository<Logger, String> {

}
