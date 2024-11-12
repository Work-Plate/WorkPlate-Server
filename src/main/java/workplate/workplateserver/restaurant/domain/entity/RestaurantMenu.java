package workplate.workplateserver.restaurant.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import workplate.workplateserver.common.BaseEntity;

/**
 * 식당 메뉴 Entity
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class RestaurantMenu extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	@Column(name = "menu_name")
	private String menuName;
	@Column(name = "menu_price")
	private int price;

	public static RestaurantMenu toEntity(Restaurant restaurant, String menuName, int price) {
		return RestaurantMenu.builder()
				.restaurant(restaurant)
				.menuName(menuName)
				.price(price)
				.build();
	}
}
