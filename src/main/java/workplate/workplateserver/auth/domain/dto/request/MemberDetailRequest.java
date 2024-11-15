package workplate.workplateserver.auth.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import workplate.workplateserver.auth.domain.MainExperience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.SubExperience;

/**
 * 회원 상세정보 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailRequest {

	private String username;
	private int age;
	private MainExperience mainExperience;
	private SubExperience subExperience;
	private MainExperience mainPreference;
	private SubExperience subPreference;
	private PhysicalStatus physicalStatus;
}
