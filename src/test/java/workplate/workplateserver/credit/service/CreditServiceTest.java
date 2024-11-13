package workplate.workplateserver.credit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.common.CommonService;
import workplate.workplateserver.credit.domain.dto.CreditResponse;
import workplate.workplateserver.credit.domain.entity.Credit;
import workplate.workplateserver.credit.repository.CreditRepository;

/**
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("크레딧 서비스 테스트")
class CreditServiceTest {

	@InjectMocks
	CreditService creditService;
	@Mock
	CreditRepository creditRepository;
	@Mock
	CommonService commonService;

	@Test
	void 회원정보를입력하면_회원이소지한크레딧을반환한다() {
	    // Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		Credit credit = Credit.toEntity(member);
		credit.plusBalance(10000L);
		given(commonService.findByUsername("testId", true)).willReturn(member);
		given(creditRepository.findByMember(member)).willReturn(Optional.of(credit));

	    // When
		CreditResponse result = creditService.findCredit(member.getUsername());

		// Then
		assertEquals(10000L, result.getBalance());
		assertEquals("testId", result.getUsername());
		verify(commonService).findByUsername("testId", true);
		verify(creditRepository).findByMember(any());
	}

	@Test
	void 회원정보와_증가시킬금액을입력하면_크레딧이증가한다() {
	    // Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		Credit credit = Credit.toEntity(member);
		credit.plusBalance(10000L);
		given(commonService.findByUsername("testId", true)).willReturn(member);
		given(creditRepository.findByMember(member)).willReturn(Optional.of(credit));

	    // When
		creditService.plusCredit(member.getUsername(), 1000L);

	    // Then
		assertEquals(11000L, credit.getBalance());
		verify(commonService).findByUsername("testId", true);
		verify(creditRepository).findByMember(any());
	}

	@Test
	@DisplayName("")
	void 회원정보와_감소시킬금액을입력하면_크레딧이감소한다() {
	    // Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		Credit credit = Credit.toEntity(member);
		credit.plusBalance(10000L);
		given(commonService.findByUsername("testId", true)).willReturn(member);
		given(creditRepository.findByMember(member)).willReturn(Optional.of(credit));

	    // When
		creditService.minusCredit(member.getUsername(), 1000L);

	    // Then
		assertEquals(9000L, credit.getBalance());
		verify(commonService).findByUsername("testId", true);
		verify(creditRepository).findByMember(any());
	}

	@Test
	@DisplayName("")
	void 회원정보와_감소시킬금액이현재금액보다크다면_예외를터트린다() {
		// Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		Credit credit = Credit.toEntity(member);
		credit.plusBalance(10000L);
		given(commonService.findByUsername("testId", true)).willReturn(member);
		given(creditRepository.findByMember(member)).willReturn(Optional.of(credit));

		// When
		assertThrows(IllegalArgumentException.class, () -> creditService.minusCredit(member.getUsername(), 11000L));

		// Then
		verify(commonService).findByUsername("testId", true);
		verify(creditRepository).findByMember(any());
	}
}
