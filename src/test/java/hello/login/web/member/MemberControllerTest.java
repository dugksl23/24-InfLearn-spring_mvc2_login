package hello.login.web.member;

import hello.login.domain.member.MemberService;
import hello.login.web.dto.RegisterMemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import javax.jdo.annotations.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MemberService memberService;
    private MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;


    @Test
    public void addMember() {

        RegisterMemberDto build = RegisterMemberDto.builder().userId("").userName("").password("").build();
        memberService.addMember(build);

        String[] messageCodes = codesResolver.resolveMessageCodes("NotBlank", "RegisterMemberDto", "userId", String.class);
//        System.out.println("message code : " + messageCodes.);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );

    }

}