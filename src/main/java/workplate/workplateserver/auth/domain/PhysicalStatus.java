package workplate.workplateserver.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 현재 건강 정보 enum
 *
 * @author : parkjihyeok
 * @since : 2024/11/03
 */
@Getter
@RequiredArgsConstructor
public enum PhysicalStatus {
	// TODO: 2024/11/3 타입에 맞게 추가하기
	TEST("테스트용");
	private final String physicalStatus;
}
