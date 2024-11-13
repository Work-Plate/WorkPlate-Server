package workplate.workplateserver.credit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 크레딧 증감 요청
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Getter
@AllArgsConstructor
@Builder
public class CreditRequest {

	private String username;
	private Long balance;


}
