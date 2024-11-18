package workplate.workplateserver.work.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import workplate.workplateserver.auth.domain.MainExperience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.SubExperience;
import workplate.workplateserver.work.domain.entity.Work;

/**
 * 소일거리 응답 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Getter
@AllArgsConstructor
@Builder
public class WorkResponse {

	private String workName;
	private String workDetail;
	private Long workCredit;
	private String location;
	private MainExperience mainCategory;
	private SubExperience subCategory;
	private PhysicalStatus physicalStatus;

	public static WorkResponse toDto(Work work) {
		return WorkResponse.builder()
				.workName(work.getWorkName())
				.workDetail(work.getWorkDetail())
				.workCredit(work.getWorkCredit())
				.location(work.getLocation())
				.mainCategory(work.getMainCategory())
				.subCategory(work.getSubCategory())
				.physicalStatus(work.getPhysicalStatus())
				.build();
	}
}
