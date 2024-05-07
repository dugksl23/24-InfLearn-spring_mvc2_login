package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Slf4j
public class LogInterceptor implements HandlerInterceptor {


    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = UUID.randomUUID().toString();

        /**
         * preHandle() : 핸들러 어답터에서 예외 발생시 컨트롤러 호출 X
         * postHandle() : 컨트롤러에서 예외 발생시 postHandle() 호출 X
         * -> afterCompletion() 으로 예외 전달.
         */
        request.setAttribute("requestURI", requestURI);
        request.setAttribute(LOG_ID, logId);

        // @RequestMapping("/") : handlerMethod, url에 매핑된 모든 메서드의 정보가 포함
        // 정적 리소스 : ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
        }

        log.info("preHandle request : [{}][{}][{}] : ", logId, requestURI, handler);
        return true;
        // 다음 핸들러 호출이 된다.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle request : ModelAndView = {}", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("afterCompletion response : [{}][{}][{}]", logId, requestURI, ex);
        if (ex != null) {
            log.info("afterCompletion error : {}", ex.toString());
        }

    }
}
