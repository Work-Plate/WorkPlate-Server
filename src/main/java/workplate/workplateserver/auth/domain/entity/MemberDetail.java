package workplate.workplateserver.auth.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import workplate.workplateserver.auth.domain.Experience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.Preference;
import workplate.workplateserver.common.BaseEntity;

/**
 * 회원의 상세 정보 Entity
 *
 * @author : parkjihyeok
 * @since : 2024/11/03
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private int age;

	@Enumerated(value = EnumType.STRING)
	private Experience experience;
	@Enumerated(value = EnumType.STRING)
	private PhysicalStatus physicalStatus;
	@Enumerated(value = EnumType.STRING)
	private Preference preference;

	public static MemberDetail toEntity(Member member, int age, Experience experience, PhysicalStatus physicalStatus,
			Preference preference) {

		return MemberDetail.builder()
				.member(member)
				.age(age)
				.experience(experience)
				.physicalStatus(physicalStatus)
				.preference(preference)
				.build();
	}
}