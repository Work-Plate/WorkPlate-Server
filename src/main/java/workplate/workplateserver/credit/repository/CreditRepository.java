package workplate.workplateserver.credit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.credit.domain.entity.Credit;

/**
 * 크레딧 Repository
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
public interface CreditRepository extends JpaRepository<Credit, Long> {

	@Query("select c from Credit c join fetch c.member where c.member = :member")
	Optional<Credit> findByMember(Member member);
}
