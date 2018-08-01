package org.code4everything.ichat.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.zhazhapan.modules.constant.ValueConsts;
import com.zhazhapan.util.Checker;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pantao
 * @since 2018-07-30
 */
public class IchatExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                         Object o, @NotNull Exception e) {
        ModelAndView mv = new ModelAndView();
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>(ValueConsts.TWO_INT);
        attributes.put("code", "502");
        attributes.put("message", e.getMessage());
        String queryString = request.getQueryString();
        attributes.put("url", request.getRequestURI() + (Checker.isEmpty(queryString) ? "" : "?" + queryString));
        view.setAttributesMap(attributes);
        mv.setView(view);
        mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mv;
    }
}
