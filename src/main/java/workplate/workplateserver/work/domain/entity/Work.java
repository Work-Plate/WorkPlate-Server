package workplate.workplateserver.work.domain.entity;

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
import workplate.workplateserver.auth.domain.MainExperience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.SubExperience;
import workplate.workplateserver.common.BaseEntity;

/**
 * 소일거리 Entity
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Work extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "work_name")
	private String workName;
	@Column(name = "work_detail")
	private String workDetail;
	@Column(name = "work_credit")
	private Long workCredit;
	private String location;
	@Column(name = "main_category")
	@Enumerated(EnumType.STRING)
	private MainExperience mainCategory;
	@Column(name = "sub_category")
	@Enumerated(EnumType.STRING)
	private SubExperience subCategory;
	@Column(name = "physical_status")
	@Enumerated(EnumType.STRING)
	private PhysicalStatus physicalStatus;

	public static Work toEntity(String workName, String workDetail, Long workCredit, String location,
			MainExperience mainCategory, SubExperience subCategory, PhysicalStatus physicalStatus) {
		return Work.builder()
				.workName(workName)
				.workDetail(workDetail)
				.workCredit(workCredit)
				.location(location)
				.mainCategory(mainCategory)
				.subCategory(subCategory)
				.physicalStatus(physicalStatus)
				.build();
	}
}
