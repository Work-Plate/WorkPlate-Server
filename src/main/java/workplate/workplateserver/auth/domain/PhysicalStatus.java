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
	NORMAL("정상"),
	PRE_FRAIL("노쇠 전"),
	FRAIL("노쇠");
	private final String physicalStatus;
}
