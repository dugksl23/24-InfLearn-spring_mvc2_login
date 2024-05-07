package hello.login.web.argumentResolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * HandlerMethodArgumentResolver
     *
     * @request HttpForm data의 파라미터 바인딩을 지원하는 메서드
     */

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // Controller 호출 전에 바인딩할 파라미터에 어노테이션이 있는지 여부 먼저 확인
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasParameterType = Member.class.isAssignableFrom(parameter.getParameterType());// 파라미터의 참조 객체 타입 : Member

        return hasParameterAnnotation && hasParameterType;
        // 둘다 만족할 경우에만, true 를 반환
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("Login ArgumentsResolver 실행");
        // 1. HttpRequest 를 가져와야 한다.
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // 2. 로그인 기능 확인 Session
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        // 세션이 유지가 되면 로그인으로 이동
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
