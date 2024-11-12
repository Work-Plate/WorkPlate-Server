package workplate.workplateserver.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import workplate.workplateserver.restaurant.domain.entity.Restaurant;
import workplate.workplateserver.restaurant.domain.entity.RestaurantMenu;

/**
 * 식당 메뉴 Repository
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {

	@Query("select rm from RestaurantMenu rm join fetch rm.restaurant where rm.restaurant = :restaurant")
	List<RestaurantMenu> findByRestaurant(Restaurant restaurant);
}
