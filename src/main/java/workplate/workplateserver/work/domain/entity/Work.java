package workplate.workplateserver.work.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

	public static Work toEntity(String workName, String workDetail, Long workCredit) {
		return Work.builder()
				.workName(workName)
				.workDetail(workDetail)
				.workCredit(workCredit)
				.build();
	}
}
