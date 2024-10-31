package workplate.workplateserver.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import workplate.workplateserver.auth.domain.dto.request.JoinRequest;
import workplate.workplateserver.auth.repository.MemberRepository;

/**
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("회원 Service 테스트")
class MemberServiceTest {

	@InjectMocks
	MemberService memberService;
	@Mock
	MemberRepository memberRepository;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	@DisplayName("회원가입 성공")
	void saveMemberTest() {
	    // Given
		JoinRequest r = new JoinRequest("testId", "testName", "testPw");
	    // When
		given(memberRepository.existsByUsername("testId")).willReturn(false);

		// Then
		assertDoesNotThrow(() -> memberService.saveMember(r));
		verify(memberRepository).existsByUsername("testId");
		verify(bCryptPasswordEncoder).encode("testPw");
		verify(memberRepository).save(any());
	}

	@Test
	@DisplayName("회원가입 실패")
	void saveMemberFailTest() {
		// Given
		JoinRequest r = new JoinRequest("testId", "testName", "testPw");

		// When
		given(memberRepository.existsByUsername("testId")).willReturn(true);

		// Then
		assertThrows(IllegalArgumentException.class, () -> memberService.saveMember(r));
		verify(memberRepository).existsByUsername("testId");
		verify(bCryptPasswordEncoder, never()).encode("testPw");
		verify(memberRepository,never()).save(any());
	}
}
