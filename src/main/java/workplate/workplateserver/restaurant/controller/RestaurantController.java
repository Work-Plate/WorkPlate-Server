package workplate.workplateserver.restaurant.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.common.ApiResponse;
import workplate.workplateserver.common.PageResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantDetailResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantResponse;
import workplate.workplateserver.restaurant.service.RestaurantService;

/**
 * 식당 컨트롤러
 *
 * @author : parkjihyeok
 * @since : 2024/11/12
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestaurantController {

	private final RestaurantService restaurantService;

	@GetMapping("/restaurants")
	public ApiResponse<PageResponse<RestaurantResponse>> findAll(Pageable pageable) {
		PageResponse<RestaurantResponse> response = restaurantService.findRestaurant(pageable);

		return ApiResponse.success(response);
	}

	@GetMapping("/restaurants/{id}")
	public ApiResponse<RestaurantDetailResponse> findDetail(@PathVariable Long id) {
		RestaurantDetailResponse response = restaurantService.findRestaurantDetail(id);

		return ApiResponse.success(response);
	}
}
