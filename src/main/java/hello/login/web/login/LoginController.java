package hello.login.web.login;


import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.dto.LoginMemberDto;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;
    private final RequestContextFilter requestContextFilter;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginMemberDto());
        return "/member/login";
    }

//    @PostMapping("/login")
    public String loginV1(@CookieValue("memberId") String memberId, @Validated @ModelAttribute("loginForm") LoginMemberDto member, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "/member/login";
        }

        Member login = loginService.login(member);
        if (login == null) {
            bindingResult.reject("loginFail");
            return "member/login";
            // reject 는 Global Error
            // rejectValue 는 Field Error
        }

        model.addAttribute("id", login.getId());

        // 별도 로그인 처리 TODO
        // 1. Cookie　에 시간 정보를 주지 않으면 세션 쿠키
        // 2. Cookie　에 만료 기간을 주면 영속 쿠키
        Cookie isCookie = new Cookie("memberId", String.valueOf(login.getId()));

        return "redirect:/";
    }


    @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute("loginForm") LoginMemberDto member, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "/member/login";
        }

        Member login = loginService.login(member);
        if (login == null) {
            bindingResult.reject("loginFail");
            return "member/login";
            // reject 는 Global Error
            // rejectValue 는 Field Error
        }

        model.addAttribute("id", login.getId());

        // 별도 로그인 처리 TODO
        sessionManager.createSession(login, response);

        return "redirect:/";
    }


//    @PostMapping("/logout")
    public String logoutV1(HttpServletRequest request) {
//        Cookie memberId = new Cookie("memberId", null);
//        memberId.setMaxAge(0); // 만료 기간 설정
//        response.addCookie(memberId);
        sessionManager.removeSession(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.removeSession(request);
        return "redirect:/";
    }

}
