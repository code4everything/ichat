package org.code4everything.ichat.config;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.model.ResultObject;
import org.code4everything.ichat.exception.IchatExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * @author pantao
 * @since 2018-07-30
 */
@Configuration
public class IchatWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".html");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(iChatExceptionHandler());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
    }

    @Bean
    public IchatExceptionHandler iChatExceptionHandler() {
        return new IchatExceptionHandler();
    }

    @Bean
    @Scope("prototype")
    public JSONObject jsonObject() {
        return new JSONObject();
    }

    @Bean
    @Scope("prototype")
    public ResultObject resultObject() {
        return new ResultObject();
    }
}
