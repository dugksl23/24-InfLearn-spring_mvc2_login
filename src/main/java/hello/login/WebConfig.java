package hello.login;


import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
//        String[] str = new String[]{"/login", "/login/*", "/logout", "/logout/*"};
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new LoginCheckFilter());
        filter.addUrlPatterns("/*");
        // whiteList 를 통해 모든 url 검증하기에 모든 요청을 filter 하도록 한다.
        // 유지보수를 위해서 whiteList 에 url 을 추가하는 것이 정책이 적절하다.
        filter.setOrder(2);
        return filter;
    }
}
