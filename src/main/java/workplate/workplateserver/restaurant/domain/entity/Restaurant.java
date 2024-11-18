package workplate.workplateserver.restaurant.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import workplate.workplateserver.common.BaseEntity;
import workplate.workplateserver.restaurant.domain.RestaurantType;

/**
 * 식당 Entity
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Restaurant extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "restaurant_name")
	private String name;
	@Column(name = "restaurant_type")
	@Enumerated(value = EnumType.STRING)
	private RestaurantType restaurantType;
	@Column(name = "restaurant_location")
	private String location;

	public static Restaurant toEntity(String name, RestaurantType restaurantType, String location) {
		return Restaurant.builder()
				.name(name)
				.restaurantType(restaurantType)
				.location(location)
				.build();
	}
}
