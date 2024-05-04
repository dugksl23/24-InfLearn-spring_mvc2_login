package hello.login.domain.member;


import hello.login.web.dto.RegisterMemberDto;
import hello.login.web.dto.ViewMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        return byId.orElse(null);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public Long addMember(RegisterMemberDto member) {
        Member memberEntity = member.createMemberEntity();
        return memberRepository.save(memberEntity).getId();
    }

    public Member update(Member member) {
        return memberRepository.save(member);
    }

    public Member findByUserName(String userName) {
        List<Member> all = memberRepository.findAll();
        return all.stream().filter(name -> name.equals(userName)).findFirst().orElse(null);
    }

    public Member findMemberByUserName(String userName) {
        Optional<Member> byUserName = memberRepository.findByUserName(userName);
        return byUserName.orElse(null);
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

}
