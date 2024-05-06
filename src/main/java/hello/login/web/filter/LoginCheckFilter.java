package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
public class LoginCheckFilter implements Filter {

    /**
     * @whiteList : 비회원 접근 가능 루트
     */
    private static final String[] whiteList = {"/", "/member/add", "/login", "/css/*", "/logout"};
    // 로그인 안되었다고 하여, 해당 파일을 호출하지 못할 수 없기에 css/* 모두 포함

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        try {
            log.info("1. 인증 체크 필터 시작 {} : ", requestURI);

            /**
             * if(isLoginCheckPath(requestURI))
             * @return true ==　로그인 된 사람
             */
            if (isLoginCheckPath(requestURI)) {
                log.info(" 2. 인증된 URL 의 로그인 체크 로직 실행 {} : ", requestURI);
                // 해당 url 은 get queryString으로 접근 가능하기에 2차적으로 로그인 여부 확인해야 한다.
                HttpSession session = request.getSession(false);

                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
//                    response.sendRedirect("/");
                    response.sendRedirect("/login?redirectURL= " + requestURI); //로그인 성공후 items 페이지로 이동
                    return;
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            throw e;
            // 예외 로깅 가능 하지만, 톰캣까지 에외를 보내주어야 한다.
        } finally {
            log.info("인증 체크 필터 종료 {} : ", requestURI);
        }



    }

    /**
     * whiteList 의 경우 인증 체크 X
     *
     * @return false = 로그인 한 사람 / 인증된 사람
     * @requestUri : 화이트 리스트에 해당하지 않은 URL 인지 확인
     */
    private boolean isLoginCheckPath(String requestUri) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestUri);
        // 화이트 리스트에 해당하지 않은 URL   -> (로그인 한 사람)
    }

}
