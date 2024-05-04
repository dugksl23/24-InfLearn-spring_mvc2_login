package hello.login.web.member;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberService;
import hello.login.web.dto.RegisterMemberDto;
import hello.login.web.dto.ViewMemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/add")
    public String addMember(@ModelAttribute("member") RegisterMemberDto member, Model model) {
        return "member/addMemberForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") RegisterMemberDto member, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            //ModelAttribute　이름으로 경로가 잡힌다.
            log.info("errors: {} ", bindingResult.getAllErrors().toString());
            return "member/addMemberForm";
        }

        memberService.addMember(member);
        return "redirect:/";
    }


}
