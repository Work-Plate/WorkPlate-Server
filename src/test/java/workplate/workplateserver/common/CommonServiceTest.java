package workplate.workplateserver.common;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.repository.MemberRepository;

/**
 * @author : parkjihyeok
 * @since : 2024/11/03
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("공통 서비스로직 테스트")
class CommonServiceTest {

	@InjectMocks
	CommonService commonService;
	@Mock
	MemberRepository memberRepository;
	@Mock
	SecurityContext securityContext;
	@Mock
	private Authentication authentication;

	@BeforeEach
	public void setup() {
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	@DisplayName("회원 객체를 반환하는 테스트 (권한 X / 예외 없음)")
	void findByUsernameTest1() {
	    // Given
		Member member = Member.toEntity("testId", "testName", "testPw");
		given(memberRepository.findByUsername("testId")).willReturn(Optional.of(member));

	    // When

		// Then
		assertDoesNotThrow(() -> commonService.findByUsername("testId", false));
		verify(memberRepository).findByUsername("testId");
	}

	@Test
	@DisplayName("회원 객체를 반환하는 테스트 (권한 X / 예외 발생)")
	void findByUsernameTest2() {
		// Given
		given(memberRepository.findByUsername("testId")).willReturn(Optional.empty());

		// When

		// Then
		assertThrows(UsernameNotFoundException.class, () -> commonService.findByUsername("testId", false));
		verify(memberRepository).findByUsername("testId");
	}

	@Test
	@DisplayName("회원 객체를 반환하는 테스트 (권한 O / 예외 없음)")
	void findByUsernameTest3() {
		// Given
		Member member = Member.toEntity("testId", "testName", "testPw");
		given(memberRepository.findByUsername("testId")).willReturn(Optional.of(member));
		given(securityContext.getAuthentication()).willReturn(authentication);
		given(authentication.getName()).willReturn("testId");

		// When

		// Then
		assertDoesNotThrow(() -> commonService.findByUsername("testId", true));
		verify(memberRepository).findByUsername("testId");
	}

	@Test
	@DisplayName("회원 객체를 반환하는 테스트 (권한 O / 예외 발생)")
	void findByUsernameTest4() {
		// Given
		Member member = Member.toEntity("testId", "testName", "testPw");
		given(memberRepository.findByUsername("testId")).willReturn(Optional.of(member));
		given(securityContext.getAuthentication()).willReturn(authentication);
		given(authentication.getName()).willReturn("testI");

		// When

		// Then
		assertThrows(AccessDeniedException.class, () -> commonService.findByUsername("testId", true));
		verify(memberRepository).findByUsername("testId");
	}
}
