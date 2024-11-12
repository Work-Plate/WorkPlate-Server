package workplate.workplateserver.restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import workplate.workplateserver.restaurant.domain.entity.Restaurant;

/**
 * 식당 Repository
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	Page<Restaurant> findAll(Pageable pageable);
}
