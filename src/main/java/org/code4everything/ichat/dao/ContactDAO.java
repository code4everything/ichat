package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018-08-07
 */
@Repository
public interface ContactDAO extends MongoRepository<Contact, String> {

}
