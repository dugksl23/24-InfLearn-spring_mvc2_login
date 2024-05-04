package hello.login.web.login;


import com.sun.xml.bind.v2.TODO;
import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.dto.LoginMemberDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String login(@Validated @ModelAttribute("loginForm") LoginMemberDto member, BindingResult bindingResult, Model model) {

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

        return "redirect:/";
    }
}
