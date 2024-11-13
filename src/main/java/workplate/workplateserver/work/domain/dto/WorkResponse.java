package workplate.workplateserver.work.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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

	public static WorkResponse toDto(Work work) {
		return WorkResponse.builder()
				.workName(work.getWorkName())
				.workDetail(work.getWorkDetail())
				.workCredit(work.getWorkCredit())
				.build();
	}
}
