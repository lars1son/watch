package com.edsson.expopromoter.api;

import com.edsson.expopromoter.api.core.filter.JwtFilter;
import com.edsson.expopromoter.api.core.filter.PermissionHandlerInterceptor;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.handler.MappedInterceptor;

//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication
@EnableScheduling
public class Application extends SpringBootServletInitializer {
    private static final Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        BasicConfigurator.configure();
        SpringApplication app = new SpringApplication(Application.class);
        app.run();
        logger.info("=================== APPLICATION STARTED ======================");
    }


    @Bean

    public FilterRegistrationBean jwtFilterRegistration( ) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtFilter());

        filterRegistrationBean.addUrlPatterns("/*");

        // ordering in the filter chain
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    // Request Interceptor for checking permission with custom annotation.
    @Bean
    public MappedInterceptor PermissionHandlerInterceptor() {
        return new MappedInterceptor(null, new PermissionHandlerInterceptor());
    }


}
