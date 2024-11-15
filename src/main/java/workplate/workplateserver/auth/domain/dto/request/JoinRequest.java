package workplate.workplateserver.auth.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

	private String username;
	private String name;
	private String password;
}
