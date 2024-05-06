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

    public ViewMemberDto createViewMemberDto(Member member) {
        return  ViewMemberDto.builder().id(member.getId()).userId(member.getUserId())
                .userName(member.getUserName()).build();
    }

}
