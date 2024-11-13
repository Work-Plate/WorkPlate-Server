package workplate.workplateserver.work.controller;

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

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import workplate.workplateserver.auth.domain.entity.Member;
import workplate.workplateserver.auth.domain.jwt.JwtTokenProvider;
import workplate.workplateserver.auth.domain.jwt.repository.JwtAccessTokenRepository;
import workplate.workplateserver.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import workplate.workplateserver.auth.repository.MemberRepository;
import workplate.workplateserver.common.PageResponse;
import workplate.workplateserver.credit.domain.dto.CreditResponse;
import workplate.workplateserver.credit.domain.entity.Credit;
import workplate.workplateserver.restaurant.service.RestaurantService;
import workplate.workplateserver.work.domain.dto.WorkJoinRequest;
import workplate.workplateserver.work.domain.dto.WorkResponse;
import workplate.workplateserver.work.service.WorkService;

/**
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@WebMvcTest(WorkController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DisplayName("소일거리 Controller 테스트")
class WorkControllerTest {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	WorkService workService;
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
	@DisplayName("소일거리 전체 조회하기")
	@WithMockUser
	void findAllTest() throws Exception {
		// Given
		WorkResponse r1 = new WorkResponse("경비", "병원을 경비합니다.", 10000L);
		WorkResponse r2 = new WorkResponse("주간경비", "병원을 주간에 경비합니다.", 11000L);
		WorkResponse r3 = new WorkResponse("야간경비", "병원을 야간에 경비합니다.", 12000L);
		PageResponse<WorkResponse> response = new PageResponse<>(List.of(r1, r2, r3));
		given(workService.findAll(any())).willReturn(response);

		// When
		mockMvc.perform(get("/api/works")
						.queryParam("page", "0"))
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
								parameterWithName("page").description("페이지 정보")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과"),
								fieldWithPath("data.content[].workName").description("소일거리 이름"),
								fieldWithPath("data.content[].workDetail").description("소일거리 상세 내용"),
								fieldWithPath("data.content[].workCredit").description("소일거리 시급"),
								fieldWithPath("data.pageNum").description("현재 페이지 번호"),
								fieldWithPath("data.pageSize").description("페이지 당 항목 수"),
								fieldWithPath("data.totalPages").description("총 페이지 수"),
								fieldWithPath("data.totalElements").description("총 항목 수"),
								fieldWithPath("data.last").description("마지막 페이지 여부 (true일 경우 마지막 페이지)")

						)));
		// Then
	}

	@Test
	@DisplayName("소일거리 상세 조회하기")
	@WithMockUser
	void findByIdTest() throws Exception {
		// Given
		WorkResponse r1 = new WorkResponse("경비", "병원을 경비합니다.", 10000L);
		given(workService.findById(1L)).willReturn(r1);

		// When
		mockMvc.perform(get("/api/works/{id}", 1))
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
								parameterWithName("id").description("소일거리 ID")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과"),
								fieldWithPath("data.workName").description("소일거리 이름"),
								fieldWithPath("data.workDetail").description("소일거리 상세 내용"),
								fieldWithPath("data.workCredit").description("소일거리 시급")
						)));
		// Then
	}

	@Test
	@DisplayName("소일거리 참가하기")
	@WithMockUser
	void joinWorkTest() throws Exception {
		// Given
		WorkJoinRequest request = new WorkJoinRequest("testId", 10L);

		// When
		mockMvc.perform(post("/api/works-join")
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
								fieldWithPath("workId").description("소일거리 ID")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과")
						)));
		// Then
	}
}
