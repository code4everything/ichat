package org.code4everything.ichat.dao;

import org.code4everything.ichat.domain.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pantao
 * @since 2018-08-03
 */
@Repository
public interface DocumentDAO extends MongoRepository<Document, String> {

    Document findDocumentByUrl(String url);

    Document findDocumentByLocal(String local);
}
