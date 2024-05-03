package hello.login.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class registerMemberDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String userName;
}
