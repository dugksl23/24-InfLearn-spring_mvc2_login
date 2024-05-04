package hello.login.domain.member;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String userId;
    private String userName;
    private String password;

    @CreationTimestamp
    private LocalDateTime registerTime;
    @UpdateTimestamp
    private LocalDateTime lastLoginTime;
    @UpdateTimestamp
    private LocalDateTime lastUpdateTime;



}
