package org.code4everything.ichat.service;

import com.zhazhapan.util.enums.LogLevel;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2018-08-01
 */
public interface CommonService {

    String uploadAvatar(String userId, MultipartFile avatar);

    String saveDocument(MultipartFile file, String userId);

    String saveDocument(String local, String url, MultipartFile file, String userId);

    boolean saveFile(String filename, MultipartFile file, String userId);

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @param code 方式方式
     */
    void sendCode(String email, String code);

    void saveLog(LogLevel level, String method, String description, String userId);

    void saveCode(String key, String code);

    String getString(String key);
}
