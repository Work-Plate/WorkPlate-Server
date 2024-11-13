package workplate.workplateserver.credit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import workplate.workplateserver.credit.domain.entity.Credit;

/**
 * 크레딧 DTO
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Getter
@AllArgsConstructor
@Builder
public class CreditResponse {

	private String username;
	private Long balance;

	public static CreditResponse toDto(Credit credit) {
		return CreditResponse.builder()
				.username(credit.getMember().getUsername())
				.balance(credit.getBalance())
				.build();
	}
}
