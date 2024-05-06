package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Session 관리
 */
@Component
@Slf4j
public class SessionManager {

    public static final String MY_SESSION_ID = "mySessionId";
    private Map<String, Object> sessionMap = new ConcurrentHashMap<>();//동시 쓰레드 지원하는 Map

    /**
     * 세션 생성 로직
     */
    public void createSession(Object value, HttpServletResponse response) {

        /**
         *  　Session ID 생성
         * @Key : UUID
         * @Value : member 식별자(id) 값
         */
        // Session ID (UUID 생성) 생성 및 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionMap.put(sessionId, value);
        // key : 멤버의 SessionId
        // value : sessionId value

        /**
         *  　Cookie 생성
         * @Key : mySessionId　(상수값)
         * @Value : sessionId의 UUID(KEY)
         */
        Cookie mySessionCookie = new Cookie(MY_SESSION_ID, sessionId);
        response.addCookie(mySessionCookie);
        // Cookie key = value(session key/uuid) = value (sessionValue/member id)
    }


    public Object getSession(HttpServletRequest request) {
        Cookie cookie = getCookieSessionId(request);
        if (cookie == null) {
            return null;
        }

        return sessionMap.get(cookie.getValue());//key: sessionId / value : uuid
        // 1. SessionId : key = UUID
        //                value = member key 값
        // 2. Cookie : key = mySessionId
        //             value = mySessionId value


    }


    private Cookie getCookieSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        /**
         * @cookieName : key 값 = SessionId
         * @Arrays. : []을 Stream 으로 변경
         */
        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(MY_SESSION_ID)).findFirst().orElse(null);

    }


    /**
     * session expired
     */
    public void removeSession(HttpServletRequest request) {
        Cookie sessionId = getCookieSessionId(request);
        if (sessionId != null) {
            sessionMap.remove(sessionId.getValue());
        }
    }

}
