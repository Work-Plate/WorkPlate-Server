package workplate.workplateserver.work.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소일거리 참가 요청
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WorkJoinRequest {

	private String username;
	private Long workId;
}
