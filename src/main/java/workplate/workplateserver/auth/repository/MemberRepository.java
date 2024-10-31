package workplate.workplateserver.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import workplate.workplateserver.auth.domain.entity.Member;

/**
 * 회원 Repostory
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByUsername(String username);
}
