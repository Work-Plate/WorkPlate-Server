package workplate.workplateserver.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import workplate.workplateserver.auth.domain.Experience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.Preference;
import workplate.workplateserver.auth.domain.dto.request.JoinRequest;
import workplate.workplateserver.auth.domain.dto.request.MemberDetailRequest;
import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.repository.MemberDetailRepository;
import workplate.workplateserver.auth.repository.MemberRepository;
import workplate.workplateserver.common.CommonService;

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
	MemberDetailRepository memberDetailRepository;
	@Mock
	CommonService commonService;
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

	@Test
	@DisplayName("회원 상세정보 저장 테스트")
	void saveDetailsTest() {
	    // Given
		MemberDetailRequest memberDetailRequest = new MemberDetailRequest("testId", 10, Experience.TEST, PhysicalStatus.TEST, Preference.TEST);
		Member m = Member.toEntity("testId", "name", "pw");
		given(commonService.findByUsername("testId", true)).willReturn(m);

	    // When
		memberService.saveDetails(memberDetailRequest);

	    // Then
		verify(commonService).findByUsername("testId", true);
		verify(memberDetailRepository).save(any());
	}

	@Test
	@DisplayName("회원 상세정보 저장 실패 테스트")
	void saveDetailsFailTest() {
		// Given
		MemberDetailRequest memberDetailRequest = new MemberDetailRequest("testId", 10, Experience.TEST, PhysicalStatus.TEST, Preference.TEST);
		Member m = Member.toEntity("testId", "name", "pw");
		given(commonService.findByUsername("testId", true)).willThrow(AccessDeniedException.class);

		// When
		assertThrows(AccessDeniedException.class, () -> memberService.saveDetails(memberDetailRequest));

		// Then
		verify(commonService).findByUsername("testId", true);
		verify(memberDetailRepository, never()).save(any());
	}
}
