package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.LogRecord;

@Slf4j
public class LogFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("LogFilter doFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try{
            log.info("Request [{}][{}] :", uuid, requestURI);
            filterChain.doFilter(servletRequest, servletResponse);
            //filterChain 의 dpoFilter는 다음 filter가 있으면 filter 호출
            // 없으면 servlet 호출
        } catch (Exception e){
            throw e;
        } finally {
            log.info("response : [{}][{}] : ", uuid, requestURI) ;
        }


    }

    @Override
    public void destroy() {
        log.info("LogFilter destroy");
    }
}
