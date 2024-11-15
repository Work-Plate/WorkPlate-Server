package workplate.workplateserver.auth.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import workplate.workplateserver.auth.domain.MainExperience;
import workplate.workplateserver.auth.domain.PhysicalStatus;
import workplate.workplateserver.auth.domain.SubExperience;
import workplate.workplateserver.auth.domain.dto.request.JoinRequest;
import workplate.workplateserver.auth.domain.dto.request.MemberDetailRequest;
import workplate.workplateserver.auth.domain.jwt.JwtTokenProvider;
import workplate.workplateserver.auth.domain.jwt.repository.JwtAccessTokenRepository;
import workplate.workplateserver.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import workplate.workplateserver.auth.repository.MemberRepository;
import workplate.workplateserver.auth.service.MemberService;

/**
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DisplayName("회원 Controller 테스트")
class MemberControllerTest {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	MemberService memberService;
	@MockBean
	MemberRepository memberRepository;
	@MockBean
	JwtAccessTokenRepository jwtAccessTokenRepository;
	@MockBean
	JwtRefreshTokenRepository jwtRefreshTokenRepository;
	@MockBean
	JwtTokenProvider jwtTokenProvider;
	Gson gson = new Gson();

	@Test
	@DisplayName("회원가입 테스트")
	@WithMockUser
	void joinTest() throws Exception {
		// Given
		JoinRequest request = new JoinRequest("testId", "이름", "password");

	    // When
		mockMvc.perform(post("/api/join")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("요청이 성공적으로 처리되었습니다."))
				.andExpect(jsonPath("$.data").exists())
				.andDo(print())
				.andDo(document("{class-name}/{method-name}/",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						queryParameters(
								parameterWithName("_csrf").description("csrf")
						),
						requestFields(
								fieldWithPath("username").description("가입할 아이디"),
								fieldWithPath("name").description("가입할 이름"),
								fieldWithPath("password").description("가입할 비밀번호")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과")
						)));

	    // Then
	}

	@Test
	@DisplayName("회원가입 실패 테스트")
	@WithMockUser
	void joinFailTest() throws Exception {
		// Given
		JoinRequest request = new JoinRequest("testId", "이름", "password");
		doThrow(new IllegalArgumentException("이미 존재하는 회원아이디입니다.")).when(memberService).saveMember(any());

		// When
		mockMvc.perform(post("/api/join")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(request)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.message").value("요청에 실패했습니다."))
				.andExpect(jsonPath("$.data").exists())
				.andDo(print())
				.andDo(document("{class-name}/{method-name}/",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						queryParameters(
								parameterWithName("_csrf").description("csrf")
						),
						requestFields(
								fieldWithPath("username").description("가입할 아이디"),
								fieldWithPath("name").description("가입할 이름"),
								fieldWithPath("password").description("가입할 비밀번호")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과")
						)));

		// Then
	}

	@Test
	@DisplayName("회원 상세정보 입력 테스트")
	@WithMockUser
	void addDetailTest() throws Exception {
		// Given
		MemberDetailRequest request = new MemberDetailRequest("testId", 70, MainExperience.OFFICE_ACCOUNTING_IT,
				SubExperience.ACCOUNTING_FINANCE, MainExperience.OFFICE_ACCOUNTING_IT, SubExperience.ACCOUNTING_FINANCE,
				PhysicalStatus.NORMAL);

		// When
		mockMvc.perform(post("/api/member")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(request)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("요청이 성공적으로 처리되었습니다."))
				.andExpect(jsonPath("$.data").exists())
				.andDo(print())
				.andDo(document("{class-name}/{method-name}/",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						queryParameters(
								parameterWithName("_csrf").description("csrf")
						),
						requestFields(
								fieldWithPath("username").description("가입한 아이디"),
								fieldWithPath("age").description("나이"),
								fieldWithPath("mainExperience").description("일 경험 (대분류)"),
								fieldWithPath("subExperience").description("일 경험 (소분류)"),
								fieldWithPath("mainPreference").description("선호 직종 (대분류)"),
								fieldWithPath("subPreference").description("선호 직종 (소분류)"),
								fieldWithPath("physicalStatus").description("건강 상태")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과")
						)));

		// Then
	}

	@Test
	@DisplayName("회원 상세정보 입력 실패 테스트")
	@WithMockUser
	void addDetailFailTest() throws Exception {
		// Given
		MemberDetailRequest request = new MemberDetailRequest("testId", 70, MainExperience.OFFICE_ACCOUNTING_IT,
				SubExperience.ACCOUNTING_FINANCE, MainExperience.OFFICE_ACCOUNTING_IT, SubExperience.ACCOUNTING_FINANCE,
				PhysicalStatus.NORMAL);
		doThrow(new UsernameNotFoundException("회원정보를 찾을 수 없습니다.")).when(memberService).saveDetails(any());

		// When
		mockMvc.perform(post("/api/member")
						.with(csrf())
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(request)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.message").value("요청에 실패했습니다."))
				.andExpect(jsonPath("$.data").exists())
				.andDo(print())
				.andDo(document("{class-name}/{method-name}/",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						queryParameters(
								parameterWithName("_csrf").description("csrf")
						),
						requestFields(
								fieldWithPath("username").description("가입한 아이디"),
								fieldWithPath("age").description("나이"),
								fieldWithPath("mainExperience").description("일 경험 (대분류)"),
								fieldWithPath("subExperience").description("일 경험 (소분류)"),
								fieldWithPath("mainPreference").description("선호 직종 (대분류)"),
								fieldWithPath("subPreference").description("선호 직종 (소분류)"),
								fieldWithPath("physicalStatus").description("건강 상태")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과")
						)));

		// Then
	}
}
