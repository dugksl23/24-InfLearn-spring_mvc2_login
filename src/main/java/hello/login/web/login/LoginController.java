package hello.login.web.login;


import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.dto.LoginMemberDto;
import hello.login.web.dto.ViewMemberDto;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;
    private final HttpSession session;

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


    //    @PostMapping("/login")
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

    //    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute("loginForm") LoginMemberDto member, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) {

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

        model.addAttribute(SessionConst.LOGIN_MEMBER, login);

        /**
         * Session　생성 TODO
         * @TRUE = default 가 true 이며, 기존 Session 을 반환 혹은 없으면 새로 생성
         * @false = 기존 session 반환 혹은 없으면 null 반환
         */
        request.getSession(true).setAttribute(SessionConst.LOGIN_MEMBER, login);
        session.setAttribute(SessionConst.LOGIN_MEMBER, login);

        ViewMemberDto viewMemberDto = new ViewMemberDto().createViewMemberDto(login);
        model.addAttribute("member", viewMemberDto);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV4(@Validated @ModelAttribute("loginForm") LoginMemberDto member, BindingResult bindingResult, @RequestParam(name = "redirectURL", defaultValue = "/") String requestURL, Model model, HttpServletRequest request, HttpServletResponse response) {

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

        model.addAttribute(SessionConst.LOGIN_MEMBER, login);

        /**
         * Session　생성 TODO
         * @TRUE = default 가 true 이며, 기존 Session 을 반환 혹은 없으면 새로 생성
         * @false = 기존 session 반환 혹은 없으면 null 반환
         */
        request.getSession(true).setAttribute(SessionConst.LOGIN_MEMBER, login);
        session.setAttribute(SessionConst.LOGIN_MEMBER, login);

        ViewMemberDto viewMemberDto = new ViewMemberDto().createViewMemberDto(login);
        model.addAttribute("member", viewMemberDto);
        log.info("redirect URL : {}", requestURL);
        return "redirect:" + requestURL;
    }


    //    @PostMapping("/logout")
    public String logoutV1(HttpServletRequest request) {
//        Cookie memberId = new Cookie("memberId", null);
//        memberId.setMaxAge(0); // 만료 기간 설정
//        response.addCookie(memberId);
        sessionManager.removeSession(request);
        return "redirect:/";
    }

    //    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.removeSession(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session1 = request.getSession(false);
        if (session1 != null) {
            session1.invalidate();
        }
        return "redirect:/";
    }

}
