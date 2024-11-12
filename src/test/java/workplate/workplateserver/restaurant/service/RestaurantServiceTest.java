package workplate.workplateserver.restaurant.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static workplate.workplateserver.restaurant.domain.dto.RestaurantResponse.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import workplate.workplateserver.common.PageResponse;
import workplate.workplateserver.restaurant.domain.RestaurantType;
import workplate.workplateserver.restaurant.domain.dto.RestaurantDetailResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantResponse;
import workplate.workplateserver.restaurant.domain.entity.Restaurant;
import workplate.workplateserver.restaurant.domain.entity.RestaurantMenu;
import workplate.workplateserver.restaurant.repository.RestaurantMenuRepository;
import workplate.workplateserver.restaurant.repository.RestaurantRepository;

/**
 * @author : parkjihyeok
 * @since : 2024/11/12
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("식당 서비스 테스트")
class RestaurantServiceTest {

	@InjectMocks
	RestaurantService restaurantService;
	@Mock
	RestaurantRepository restaurantRepository;
	@Mock
	RestaurantMenuRepository restaurantMenuRepository;

	@Test
	void 페이지정보를넘기면_식당리스트를반환한다() {
		// Given
		// 1페이지, 페이지 크기는 3
		Pageable pageable = PageRequest.of(1, 3);
		Restaurant r1 = Restaurant.toEntity("가식당", RestaurantType.KOREAN_FOOD, "서울");
		Restaurant r2 = Restaurant.toEntity("나식당", RestaurantType.WESTERN_FOOD, "부산");
		Restaurant r3 = Restaurant.toEntity("다식당", RestaurantType.JAPANESE_FOOD, "울산");
		PageImpl<Restaurant> page = new PageImpl<>(List.of(r1, r2, r3), pageable, 10);
		given(restaurantRepository.findAll(pageable)).willReturn(page);

		// When
		PageResponse<RestaurantResponse> result = restaurantService.findRestaurant(pageable);

		// Then
		assertEquals(1, result.getPageNum());
		assertEquals(3, result.getPageSize());
		assertEquals(10, result.getTotalElements());
		assertAll(
				() -> assertEquals(toDto(r1).getRestaurantType(), result.getContent().get(0).getRestaurantType()),
				() -> assertEquals(toDto(r1).getName(), result.getContent().get(0).getName()),
				() -> assertEquals(toDto(r1).getLocation(), result.getContent().get(0).getLocation())
		);
		assertAll(
				() -> assertEquals(toDto(r2).getRestaurantType(), result.getContent().get(1).getRestaurantType()),
				() -> assertEquals(toDto(r2).getName(), result.getContent().get(1).getName()),
				() -> assertEquals(toDto(r2).getLocation(), result.getContent().get(1).getLocation())
		);
		assertAll(
				() -> assertEquals(toDto(r3).getRestaurantType(), result.getContent().get(2).getRestaurantType()),
				() -> assertEquals(toDto(r3).getName(), result.getContent().get(2).getName()),
				() -> assertEquals(toDto(r3).getLocation(), result.getContent().get(2).getLocation())
		);
		verify(restaurantRepository).findAll(pageable);
	}

	@Test
	void 식당ID를넘기면_식당의상세정보를반환한다() {
		// Given
		Restaurant r1 = Restaurant.toEntity("가식당", RestaurantType.KOREAN_FOOD, "서울");
		RestaurantMenu rm1 = RestaurantMenu.toEntity(r1, "김밥", 1000);
		RestaurantMenu rm2 = RestaurantMenu.toEntity(r1, "제육볶음", 2000);
		RestaurantMenu rm3 = RestaurantMenu.toEntity(r1, "우동", 3000);
		RestaurantMenu rm4 = RestaurantMenu.toEntity(r1, "떡볶이", 4000);
		given(restaurantRepository.findById(10L)).willReturn(Optional.ofNullable(r1));
		given(restaurantMenuRepository.findByRestaurant(r1)).willReturn(List.of(rm1, rm2, rm3, rm4));

		// When
		RestaurantDetailResponse result = restaurantService.findRestaurantDetail(10L);

		// Then
		assertAll(
				() -> assertEquals(r1.getRestaurantType(), result.getRestaurantType()),
				() -> assertEquals(r1.getName(), result.getName()),
				() -> assertEquals(r1.getLocation(), result.getLocation()),
				() -> assertEquals(4, result.getMenuList().size())
		);
		verify(restaurantRepository).findById(any());
		verify(restaurantMenuRepository).findByRestaurant(any());
	}

	@Test
	void 잘못된식당ID를넘기면_예외를던진다() {
		// Given
		given(restaurantRepository.findById(10L)).willReturn(Optional.empty());

		// When
		assertThrows(IllegalArgumentException.class, () -> restaurantService.findRestaurantDetail(10L));

		// Then
	}
}
