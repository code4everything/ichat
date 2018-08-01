package org.code4everything.ichat.service.impl;

import com.zhazhapan.util.MailSender;
import com.zhazhapan.util.RandomUtils;
import org.code4everything.ichat.service.CommonService;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2018-08-01
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public boolean sendCode(String email, int method) {
        try {
            MailSender.sendMail(email, "验证码", String.valueOf(method + RandomUtils.getRandomInteger(6)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
