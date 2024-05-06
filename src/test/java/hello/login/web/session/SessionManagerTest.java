package hello.login.web.session;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberService;
import hello.login.web.dto.LoginMemberDto;
import hello.login.web.dto.RegisterMemberDto;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionManagerTest {

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LoginService loginService;

    @Test
    public void getSessionTest() {

        MockHttpServletResponse response = new MockHttpServletResponse();
        // given...
        RegisterMemberDto member = new RegisterMemberDto();
        member.setPassword("1234");
        member.setUserId("ddd");
        member.setUserName("ddddd");
        Long l = memberService.addMember(member);
        Member byId = memberService.findById(l);

        LoginMemberDto memberDto = new LoginMemberDto();
        memberDto.setUserId(byId.getUserId());
        memberDto.setPassword(byId.getPassword());

        // when...
        Member login = loginService.login(memberDto);
        sessionManager.createSession(login, response);

        // 로그인 유지 기능
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // then 서버에서 session 조회
        // 로그인 객체와 세션 객체가 같은지 확인
        Object session = sessionManager.getSession(request);
        Assertions.assertThat(session).isEqualTo(login);

        // 세션 만료
        sessionManager.removeSession(request);
        Object session1 = sessionManager.getSession(request);
        assertNull(session1);

    }

}