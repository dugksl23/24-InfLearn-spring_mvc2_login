package hello.login.web.dto;

import hello.login.domain.member.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterMemberDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String userName;

    public Member createMemberEntity() {
        return Member.builder().userId(this.userId).password(this.password).userName(this.userName).build();
    }

}
