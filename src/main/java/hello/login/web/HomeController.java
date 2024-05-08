package hello.login.web;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.domain.member.MemberService;
import hello.login.web.argumentResolver.Login;
import hello.login.web.dto.ViewMemberDto;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
    private final LoginService loginService;
    private final MemberService memberService;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    //    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        // Cookie 값 입력을 false 로 하는 이유는, 로그인을 하지 않는 사용자도 있기 때문이다.
        if (memberId == null) {
            return "home";
        }

        Optional<Member> byId = memberRepository.findById(memberId);
        if (!byId.isPresent()) {
            return "home";
        }

        // 로그인했을 시에, 해당 view에
        ViewMemberDto viewMemberDto = new ViewMemberDto().createViewMemberDto(byId.get());
        model.addAttribute("member", viewMemberDto);
        return "/member/loginHome";

    }

    //    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        Member member = (Member) sessionManager.getSession(request);
        if (member == null) {
            return "home";
        }

        // 로그인했을 시에, 해당 view에
        ViewMemberDto viewMemberDto = new ViewMemberDto().createViewMemberDto(member);
        model.addAttribute("member", viewMemberDto);
        return "/member/loginHome";

    }

    //    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        /**
         * request.getSession(false);
         * @false : 단순 방문일 경우에는 Session 생성을 하지 않도록 하기에 -> false
         */
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        // 로그인했을 시에, 해당 view에
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "home";
        }

        ViewMemberDto viewMemberDto = new ViewMemberDto().createViewMemberDto(member);
        model.addAttribute("member", viewMemberDto);
        return "/member/loginHome";

    }

    //    @GetMapping("/")
    public String homeLoginV4(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        /**
         * request.getSession(false);
         * @false : 단순 방문일 경우에는 Session 생성을 하지 않도록 하기에 -> false
         */
        if (loginMember == null) {
            return "home";
        }

        ViewMemberDto viewMemberDto = new ViewMemberDto().createViewMemberDto(loginMember);
        model.addAttribute("member", viewMemberDto);
        return "/member/loginHome";

    }

    @GetMapping("/")
    public String homeLoginV5ArgumentResolver(@Login Member loginMember, Model model) {
        /**
         * request.getSession(false);
         * @false : 단순 방문일 경우에는 Session 생성을 하지 않도록 하기에 -> false
         */
        if (loginMember == null) {
            return "home";
        }

        ViewMemberDto viewMemberDto = new ViewMemberDto().createViewMemberDto(loginMember);
        model.addAttribute("member", viewMemberDto);
        return "/member/loginHome";

    }


}