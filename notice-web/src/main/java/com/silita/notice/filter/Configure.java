package com.silita.notice.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置类，暂时用于加入过滤器Filter
 * Created by zhushuai on 2019/6/10.
 */
@Configuration
public class Configure {

    @Bean
    public FilterRegistrationBean loginFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        LoginFilter loginFilter = new LoginFilter();
        registrationBean.setFilter(loginFilter);
        List urls = new ArrayList();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }

}
