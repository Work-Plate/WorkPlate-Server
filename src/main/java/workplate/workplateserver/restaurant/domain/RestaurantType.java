package workplate.workplateserver.restaurant.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 식당 Type
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
@Getter
@RequiredArgsConstructor
public enum RestaurantType {
	KOREAN_FOOD("한식"),
	WESTERN_FOOD("양식"),
	JAPANESE_FOOD("일식"),
	CHINESE_FOOD("중식");
	private final String type;
}
