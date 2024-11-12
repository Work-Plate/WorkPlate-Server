package workplate.workplateserver.restaurant.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import workplate.workplateserver.restaurant.domain.RestaurantType;
import workplate.workplateserver.restaurant.domain.entity.Restaurant;

/**
 * 식당 상세 정보 응답 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/11/12
 */
@Getter
@AllArgsConstructor
@Builder
public class RestaurantDetailResponse {

	private String name;
	private RestaurantType restaurantType;
	private String location;
	private List<RestaurantMenuResponse> menuList;

	public static RestaurantDetailResponse toDto(Restaurant restaurant, List<RestaurantMenuResponse> menuList) {
		return RestaurantDetailResponse.builder()
				.name(restaurant.getName())
				.restaurantType(restaurant.getRestaurantType())
				.location(restaurant.getLocation())
				.menuList(menuList)
				.build();
	}
}
