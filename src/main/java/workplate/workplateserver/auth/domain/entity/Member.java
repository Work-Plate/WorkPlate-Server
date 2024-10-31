package workplate.workplateserver.auth.domain.entity;

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
import workplate.workplateserver.auth.domain.UserRole;
import workplate.workplateserver.auth.domain.dto.request.JoinRequest;
import workplate.workplateserver.common.BaseEntity;

/**
 * 회원 엔티티
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false)
	private String username;
	private String name;
	@Enumerated(value = EnumType.STRING)
	private UserRole userRole;
	private String password;

	public static Member toEntity(String username, String name, String password) {
		return Member.builder()
				.username(username)
				.name(name)
				.userRole(UserRole.ROLE_USER)
				.password(password)
				.build();
	}
}
