package hello.login.web.login;


import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.dto.LoginMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginMemberDto());
        return "/member/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginMemberDto member, BindingResult bindingResult, Model model, HttpServletResponse response) {

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
        response.addCookie(isCookie);

        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie memberId = new Cookie("memberId", null);
        memberId.setMaxAge(0); // 만료 기간 설정
        response.addCookie(memberId);
        return "redirect:/";
    }
}
