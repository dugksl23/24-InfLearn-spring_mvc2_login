package hello.login.web.interceptor;

import com.querydsl.core.util.StringUtils;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String loginId = UUID.randomUUID().toString();


        HttpSession session = request.getSession(true);

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미 인증 사용자 요청");
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false; // 더이상 컨트롤러 혹은 인터셉터 호출 하지 않고 종료하겠다는 뜻.
        }

        return true;
    }


}
