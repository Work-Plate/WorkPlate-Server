package workplate.workplateserver.auth.domain.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import workplate.workplateserver.auth.domain.Experience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.Preference;

/**
 * 회원 상세정보 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@Getter
@AllArgsConstructor
public class MemberDetailRequest {

	private String username;
	private int age;
	private Experience experience;
	private PhysicalStatus physicalStatus;
	private Preference preference;
}
