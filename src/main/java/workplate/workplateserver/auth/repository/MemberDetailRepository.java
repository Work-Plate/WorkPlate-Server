package workplate.workplateserver.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import workplate.workplateserver.auth.domain.entity.MemberDetail;

/**
 * 회원 상세정보 Repository
 *
 * @author : parkjihyeok
 * @since : 2024/11/03
 */
public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {
}
