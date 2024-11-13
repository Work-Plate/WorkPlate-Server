package workplate.workplateserver.credit.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
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
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.domain.jwt.JwtTokenProvider;
import workplate.workplateserver.auth.domain.jwt.repository.JwtAccessTokenRepository;
import workplate.workplateserver.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import workplate.workplateserver.auth.repository.MemberRepository;
import workplate.workplateserver.credit.domain.dto.CreditRequest;
import workplate.workplateserver.credit.domain.dto.CreditResponse;
import workplate.workplateserver.credit.domain.entity.Credit;
import workplate.workplateserver.credit.service.CreditService;
import workplate.workplateserver.restaurant.service.RestaurantService;

/**
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@WebMvcTest(CreditController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DisplayName("크레딧 Controller 테스트")
class CreditControllerTest {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	CreditService creditService;
	Gson gson = new Gson();

	// 필터 적용에 따른 목 선언
	@MockBean
	MemberRepository memberRepository;
	@MockBean
	JwtAccessTokenRepository jwtAccessTokenRepository;
	@MockBean
	JwtRefreshTokenRepository jwtRefreshTokenRepository;
	@MockBean
	JwtTokenProvider jwtTokenProvider;
	// 필터 적용에 따른 목 선언

	@Test
	@DisplayName("회원ID를 입력하면 회원의 크레딧 잔고가 반환된다.")
	@WithMockUser
	void findCreditTest() throws Exception {
	    // Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		Credit credit = Credit.toEntity(member);
		CreditResponse response = CreditResponse.toDto(credit);
		given(creditService.findCredit("testId")).willReturn(response);

	    // When
		mockMvc.perform(get("/api/credits/{username}", "testId"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("요청이 성공적으로 처리되었습니다."))
				.andExpect(jsonPath("$.data").exists())
				.andDo(print())
				.andDo(document("{class-name}/{method-name}/",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						pathParameters(
								parameterWithName("username").description("회원ID")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과"),
								fieldWithPath("data.username").description("회원 ID"),
								fieldWithPath("data.balance").description("가지고 있는 잔액")
						)));

	    // Then
	}

	@Test
	@DisplayName("회원ID와 증가시킬 금액을 전달하면 금액이 추가된다.")
	@WithMockUser
	void plusCreditTest() throws Exception {
		// Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		Credit credit = Credit.toEntity(member);
		credit.plusBalance(1000L);
		CreditResponse response = CreditResponse.toDto(credit);

		CreditRequest request = new CreditRequest("testId", 1000L);
		given(creditService.plusCredit("testId", 1000L)).willReturn(response);

		// When
		mockMvc.perform(patch("/api/credits/plus")
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
						requestFields(
								fieldWithPath("username").description("회원 ID"),
								fieldWithPath("balance").description("증감액")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과"),
								fieldWithPath("data.username").description("회원 ID"),
								fieldWithPath("data.balance").description("가지고 있는 잔액")
						)));

		// Then
	}

	@Test
	@DisplayName("회원ID와 감소시킬 금액을 전달하면 금액이 감소된다.")
	@WithMockUser
	void minusCreditTest() throws Exception {
		// Given
		Member member = Member.toEntity("testId", "회원A", "pw");
		Credit credit = Credit.toEntity(member);
		credit.plusBalance(1000L);
		CreditResponse response = CreditResponse.toDto(credit);

		CreditRequest request = new CreditRequest("testId", 1000L);
		given(creditService.minusCredit("testId", 1000L)).willReturn(response);

		// When
		mockMvc.perform(patch("/api/credits/minus")
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
						requestFields(
								fieldWithPath("username").description("회원 ID"),
								fieldWithPath("balance").description("증감액")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과"),
								fieldWithPath("data.username").description("회원 ID"),
								fieldWithPath("data.balance").description("가지고 있는 잔액")
						)));

		// Then
	}
}
