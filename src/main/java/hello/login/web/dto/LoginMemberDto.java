package hello.login.web.dto;


import hello.login.domain.member.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
//setter가 없어서 필드 바인딩을 통해 값이 채워지지 않은 채 객체 생성됨...
public class LoginMemberDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;

    public Member createMemberEntity() {
        return Member.builder().userId(this.userId).password(this.password).build();
    }

}
