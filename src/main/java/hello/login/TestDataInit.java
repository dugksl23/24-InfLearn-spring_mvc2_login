package hello.login;

import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberService;
import hello.login.web.dto.RegisterMemberDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberService memberService;
    private final Logger log = LoggerFactory.getLogger("myLogger");
    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
        RegisterMemberDto build = RegisterMemberDto.builder()
                .userId("user1")
                .userName("yohan")
                .password("1234").build();
        Long l = memberService.addMember(build);
        Member byId = memberService.findById(l);
        log.info("member added : {}", byId.getId());
    }

}