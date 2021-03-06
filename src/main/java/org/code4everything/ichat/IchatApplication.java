package org.code4everything.ichat;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.zhazhapan.util.ThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author pantao
 * @since 2018/7/25
 */
@SpringBootApplication
@EnableSwagger2Doc
public class IchatApplication {

    public static void main(String[] args) {
        ThreadPool.setCorePoolSize(10);
        ThreadPool.setMaximumPoolSize(100);
        ThreadPool.init();
        SpringApplication.run(IchatApplication.class, args);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
