package workplate.workplateserver.auth.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import workplate.workplateserver.auth.domain.entity.Member;

/**
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@DataJpaTest
@Transactional
@DisplayName("회원 Repository 테스트")
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	@DisplayName("회원 저장 테스트")
	void saveTest() {
	    // Given
		Member member = Member.toEntity("testId", "이름", "pw");

	    // When
		memberRepository.save(member);

	    // Then
		Member result = memberRepository.findById(member.getId()).get();
		assertAll(

				() -> assertEquals(member.getUsername(), result.getUsername()),
				() -> assertEquals(member.getName(), result.getName()),
				() -> assertEquals(member.getPassword(), result.getPassword())
		);
	}

	@Test
	@DisplayName("회원 삭제 테스트")
	void deleteTest() {
		// Given
		Member member = Member.toEntity("testId", "이름", "pw");
		memberRepository.save(member);

		// When
		memberRepository.deleteById(member.getId());

	    // Then
		assertEquals(Optional.empty(), memberRepository.findById(member.getId()));
	}

	@Test
	@DisplayName("회원이 존재하는지 테스트")
	void existsByUsernameTest() {
	    // Given
		Member member = Member.toEntity("testId", "이름", "pw");
		memberRepository.save(member);

	    // When

	    // Then
		assertTrue(memberRepository.existsByUsername("testId"));
		assertFalse(memberRepository.existsByUsername("testI"));
	}
}
