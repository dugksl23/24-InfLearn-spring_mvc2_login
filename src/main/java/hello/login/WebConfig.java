package hello.login;


import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * @whiteList : 비회원 접근 가능 루트
     */
    private static final String[] whiteList = {"/", "/member/add", "/login", "/logout", "/css/**", "/*.ico", "/error"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        // Interceptor 등록
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**") // 서블릿 url 패턴과 다름.
                .order(1)
                .excludePathPatterns("/css/**", "/*.ico", "/*.jpg", "/*.png", "/*.gif", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(whiteList);

    }

    //    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
//        String[] str = new String[]{"/login", "/login/*", "/logout", "/logout/*"};
        return filterRegistrationBean;
    }

//    @Bean
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
