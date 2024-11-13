package workplate.workplateserver.work.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import workplate.workplateserver.work.domain.entity.Work;

/**
 * 소일거리 Repository
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
public interface WorkRepository extends JpaRepository<Work, Long> {

	Page<Work> findAll(Pageable pageable);
}
