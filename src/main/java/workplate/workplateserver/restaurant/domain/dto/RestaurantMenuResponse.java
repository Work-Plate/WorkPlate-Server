package workplate.workplateserver.restaurant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import workplate.workplateserver.restaurant.domain.entity.RestaurantMenu;

/**
 * 식당 메뉴 응답 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/11/12
 */
@Getter
@AllArgsConstructor
@Builder
public class RestaurantMenuResponse {
	private String menuName;
	private int price;

	public static RestaurantMenuResponse toDto(RestaurantMenu restaurantMenu) {
		return RestaurantMenuResponse.builder()
				.menuName(restaurantMenu.getMenuName())
				.price(restaurantMenu.getPrice())
				.build();
	}
}
