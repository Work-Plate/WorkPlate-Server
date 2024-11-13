package workplate.workplateserver.work.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.common.CommonService;
import workplate.workplateserver.common.PageResponse;
import workplate.workplateserver.credit.repository.CreditRepository;
import workplate.workplateserver.credit.service.CreditService;
import workplate.workplateserver.work.domain.dto.WorkResponse;
import workplate.workplateserver.work.domain.entity.Work;
import workplate.workplateserver.work.domain.entity.WorkMember;
import workplate.workplateserver.work.repository.WorkMemberRepository;
import workplate.workplateserver.work.repository.WorkRepository;

/**
 * 소일거리 서비스
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Service
@RequiredArgsConstructor
public class WorkService {

	private final CommonService commonService;
	private final WorkRepository workRepository;
	private final WorkMemberRepository workMemberRepository;
	private final CreditService creditService;

	/**
	 * 소일거리 전체 조회 메서드
	 *
	 * @param pageable 페이지 정보
	 * @return 페이징 처리된 소일거리
	 */
	public PageResponse<WorkResponse> findAll(Pageable pageable) {
		Page<Work> findAll = workRepository.findAll(pageable);
		List<WorkResponse> content = findAll.map(WorkResponse::toDto).toList();
		PageResponse<WorkResponse> response = new PageResponse<>(content);
		response.setPageInfo(findAll);
		return response;
	}

	/**
	 * 소일거리 단건 조회
	 *
	 * @param id 소일거리 ID
	 * @return 소일거리 정보
	 */
	public WorkResponse findById(Long id) {
		return WorkResponse.toDto(findWork(id));
	}

	/**
	 * 소일거리 참가 메서드
	 *
	 * @param username 참가자 ID
	 * @param id 소일거리 ID
	 */
	@Transactional
	public void joinWork(String username, Long id) {
		Member member = commonService.findByUsername(username, true);
		Work work = findWork(id);

		WorkMember workMember = WorkMember.toEntity(member, work);
		workMemberRepository.save(workMember);
		creditService.plusCredit(username, work.getWorkCredit());
	}

	/**
	 * 소일거리를 찾아오는 메서드
	 *
	 * @param id 소일거리 ID
	 * @return 소일거리 Entity
	 */
	private Work findWork(Long id) {
		return workRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 소일거리를 찾을 수 없습니다."));
	}
}
