package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.dto.ViewMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")
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

}