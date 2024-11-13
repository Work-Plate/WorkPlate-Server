package workplate.workplateserver.work.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import workplate.workplateserver.work.domain.entity.WorkMember;

/**
 * 소일거리 참가자 Repository
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
public interface WorkMemberRepository extends JpaRepository<WorkMember, Long> {
}
