package hello.login.web.session;

import hello.login.domain.member.Member;
import hello.login.domain.member.QMember;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@Slf4j
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없어요.";
        }


        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name = {}, value = {}name", name, session.getAttribute(name)));

        Member attribute = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        log.info("session id = {}", session.getId());
        log.info("session value = {}", attribute.getId());
        log.info("session ip = {}", request.getRemoteAddr());
        log.info("getMaxInactiveInterval = {}", session.getMaxInactiveInterval());
        log.info("creation time = {}", new Date(session.getCreationTime()));
        log.info("lastAccessTime = {}", new Date(session.getLastAccessedTime()));
        log.info("isNew = {}", session.isNew());

        return "세션 추가됨";
    }
}
