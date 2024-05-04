package hello.login.web.dto;


import hello.login.domain.member.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewMemberDto {

    private Long id;
    private String userId;
    private String userName;
    private String password;

    public ViewMemberDto createViewMemberDto(Member member) {
        return ViewMemberDto.builder().id(this.id).userId(this.userId)
                .userName(this.userName).password(this.password).build();
    }

}
