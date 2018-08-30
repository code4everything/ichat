package org.code4everything.ichat.web;

import com.zhazhapan.util.model.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.code4everything.ichat.constant.IchatValueConsts;
import org.code4everything.ichat.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pantao
 * @since 2018/8/30
 */
@RestController
@RequestMapping("/file")
@Api(value = "/file", description = "文档（文件）操作相关")
public class DocumentController {

    private final CommonService commonService;

    private final HttpServletRequest request;

    @Autowired
    public DocumentController(CommonService commonService, HttpServletRequest request) {
        this.commonService = commonService;
        this.request = request;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("文件上传")
    public ResultObject upload(MultipartFile file) {
        String userId = request.getSession().getAttribute(IchatValueConsts.ID_STR).toString();
        String url = commonService.saveDocument(file, userId);
        ResultObject resultObject = new ResultObject();
        if (url.startsWith(IchatValueConsts.AVATAR_MAPPING)) {
            // 文件上传成功
            resultObject.data = url;
        } else {
            // 文件上传失败
            resultObject.code = 407;
            resultObject.message = url;
        }
        return resultObject;
    }
}
