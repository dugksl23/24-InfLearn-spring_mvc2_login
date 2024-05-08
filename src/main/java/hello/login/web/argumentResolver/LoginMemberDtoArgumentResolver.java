package hello.login.web.argumentResolver;

import hello.login.web.SessionConst;
import hello.login.web.dto.LoginMemberDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginMemberDtoArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        // 1. 어노테이션 여부 검사
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasParameterType = LoginMemberDto.class.isAssignableFrom(parameter.getParameterType());

        return hasParameterType && hasParameterAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 1. HttpServletRequest 다운 캐스팅을 통해 getPrameter();
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // 2. Session 여부 확인 || 로그인 실패시에 별도 세션 생성하지 않기 위해 false
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return Optional.ofNullable(session.getAttribute(SessionConst.LOGIN_MEMBER));
    }
}
