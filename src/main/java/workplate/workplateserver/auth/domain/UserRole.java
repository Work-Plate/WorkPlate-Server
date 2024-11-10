package workplate.workplateserver.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 회원 등급
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Getter
@RequiredArgsConstructor
public enum UserRole {
	ROLE_ADMIN("관리자"), ROLE_USER("일반 사용자");
	private final String role;
}
