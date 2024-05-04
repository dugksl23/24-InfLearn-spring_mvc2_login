package hello.login.domain.login;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.dto.LoginMemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(LoginMemberDto loginMemberDto) {
        Optional<Member> byId = memberRepository.findByUserIdAndPassword(loginMemberDto.getUserId(), loginMemberDto.getPassword());
        //byId.get(); null 일 경우, getter를 통해서 NullPointException 에러가 발생함.
        return byId.filter(member -> member.getPassword().equals(loginMemberDto.getPassword())).stream().findFirst().orElse(null);
    }
}
