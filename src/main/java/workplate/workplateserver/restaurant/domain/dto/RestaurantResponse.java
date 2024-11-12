package workplate.workplateserver.restaurant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import workplate.workplateserver.restaurant.domain.RestaurantType;
import workplate.workplateserver.restaurant.domain.entity.Restaurant;

/**
 * 식당 정보 응답 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
@Getter
@AllArgsConstructor
@Builder
public class RestaurantResponse {

	private String name;
	private RestaurantType restaurantType;
	private String location;

	public static RestaurantResponse toDto(Restaurant restaurant) {
		return RestaurantResponse.builder()
				.name(restaurant.getName())
				.restaurantType(restaurant.getRestaurantType())
				.location(restaurant.getLocation())
				.build();
	}
}
