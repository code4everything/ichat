package org.code4everything.ichat.web;

import io.swagger.annotations.Api;
import org.code4everything.ichat.dao.ContactDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pantao
 * @since 2018-08-07
 */
@RestController
@RequestMapping("/user/contact")
@Api("联系人相关操作")
public class ContactController {

    private final ContactDAO contactDAO;

    @Autowired
    public ContactController(ContactDAO contactDAO) {this.contactDAO = contactDAO;}


}
