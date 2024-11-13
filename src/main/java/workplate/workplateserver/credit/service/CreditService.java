package workplate.workplateserver.credit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.common.CommonService;
import workplate.workplateserver.credit.domain.dto.CreditResponse;
import workplate.workplateserver.credit.domain.entity.Credit;
import workplate.workplateserver.credit.repository.CreditRepository;

/**
 * 크레딧 서비스
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@Service
@RequiredArgsConstructor
public class CreditService {

	private final CreditRepository creditRepository;
	private final CommonService commonService;

	/**
	 * 한 회원의 크레딧 조회
	 *
	 * @param username 회원 ID
	 * @return 크레딧 조회 결과
	 */
	public CreditResponse findCredit(String username) {
		Credit credit = getCredit(username);
		return CreditResponse.toDto(credit);
	}

	/**
	 * 크레딧 증가 메서드
	 *
	 * @param username 회원 ID
	 * @param balance 증가할 양
	 */
	@Transactional
	public CreditResponse plusCredit(String username, Long balance) {
		Credit credit = getCredit(username);
		credit.plusBalance(balance);
		return CreditResponse.toDto(credit);
	}

	/**
	 * 크레딧 감소 메서드
	 *
	 * @param username 회원 ID
	 * @param balance 감소할 양
	 */
	@Transactional
	public CreditResponse minusCredit(String username, Long balance) {
		Credit credit = getCredit(username);
		credit.minusBalance(balance);
		return CreditResponse.toDto(credit);
	}

	/**
	 * 크레딧을 가져오는 메서드
	 *
	 * @param username 회원 ID
	 * @return 크레딧
	 */
	private Credit getCredit(String username) {
		Member member = commonService.findByUsername(username, true);
		return creditRepository.findByMember(member)
				.orElseThrow(() -> new IllegalArgumentException("해당 회원의 크레딧 정보가 없습니다."));
	}
}
