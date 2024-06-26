package hello.login.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByUserName(String userName);

    public Optional<Member> findByUserIdAndPassword(String userId, String password);

}
