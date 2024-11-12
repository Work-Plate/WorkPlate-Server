package workplate.workplateserver.restaurant.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.common.PageResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantDetailResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantMenuResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantResponse;
import workplate.workplateserver.restaurant.domain.entity.Restaurant;
import workplate.workplateserver.restaurant.repository.RestaurantMenuRepository;
import workplate.workplateserver.restaurant.repository.RestaurantRepository;

/**
 * 식당 Service
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
@Service
@RequiredArgsConstructor
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final RestaurantMenuRepository restaurantMenuRepository;

	/**
	 * 식당을 페이지별로 찾아 반환하는 메서드
	 *
	 * @param pageable 페이지 정보
	 * @return 페이징처리를 완료한 식당 정보
	 */
	public PageResponse<RestaurantResponse> findRestaurant(Pageable pageable) {
		Page<Restaurant> getPage = restaurantRepository.findAll(pageable);

		PageResponse<RestaurantResponse> response = new PageResponse<>(
				getPage.getContent()
						.stream()
						.map(RestaurantResponse::toDto)
						.toList()
		);

		response.setPageInfo(getPage);
		return response;
	}

	/**
	 * 식당 상세정보를 반환하는 메서드
	 *
	 * @param id 식당 ID
	 * @return 식당 상세 정보
	 */
	public RestaurantDetailResponse findRestaurantDetail(Long id) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 식당 정보를 찾을 수 없습니다."));

		List<RestaurantMenuResponse> menuList = restaurantMenuRepository.findByRestaurant(restaurant)
				.stream()
				.map(RestaurantMenuResponse::toDto)
				.toList();

		return RestaurantDetailResponse.toDto(restaurant, menuList);
	}
}
