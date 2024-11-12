package workplate.workplateserver.restaurant.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import workplate.workplateserver.restaurant.domain.RestaurantType;
import workplate.workplateserver.restaurant.domain.entity.Restaurant;
import workplate.workplateserver.restaurant.domain.entity.RestaurantMenu;

/**
 * @author : parkjihyeok
 * @since : 2024/11/12
 */
@DataJpaTest
@DisplayName("식당 메뉴 Repository Test")
class RestaurantMenuRepositoryTest {

	@Autowired
	EntityManager em;
	@Autowired
	RestaurantRepository restaurantRepository;
	@Autowired
	RestaurantMenuRepository restaurantMenuRepository;

	@Test
	void 식당정보를넘기면_식당메뉴를페치조인으로가져온다() {
		// Given
		Restaurant r1 = Restaurant.toEntity("가식당", RestaurantType.KOREAN_FOOD, "서울");
		RestaurantMenu rm1 = RestaurantMenu.toEntity(r1, "김밥", 1000);
		RestaurantMenu rm2 = RestaurantMenu.toEntity(r1, "제육볶음", 2000);
		RestaurantMenu rm3 = RestaurantMenu.toEntity(r1, "우동", 3000);
		RestaurantMenu rm4 = RestaurantMenu.toEntity(r1, "떡볶이", 4000);

		restaurantRepository.save(r1);
		restaurantMenuRepository.save(rm1);
		restaurantMenuRepository.save(rm2);
		restaurantMenuRepository.save(rm3);
		restaurantMenuRepository.save(rm4);

		em.flush();
		em.clear();

		// When
		List<RestaurantMenu> result = restaurantMenuRepository.findByRestaurant(r1);

		// Then
		assertEquals(4, result.size());
		assertEquals(r1.getName(), result.get(0).getRestaurant().getName());
		assertEquals(r1.getRestaurantType(), result.get(0).getRestaurant().getRestaurantType());
		assertEquals(r1.getLocation(), result.get(0).getRestaurant().getLocation());
		assertEquals(rm2.getMenuName(), result.get(1).getMenuName());
	}
}
