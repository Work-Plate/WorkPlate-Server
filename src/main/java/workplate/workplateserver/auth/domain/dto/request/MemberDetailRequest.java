package workplate.workplateserver.auth.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 상세정보 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Getter
@AllArgsConstructor
public class MemberDetailRequest {

	private String username;
	private int age;

}
