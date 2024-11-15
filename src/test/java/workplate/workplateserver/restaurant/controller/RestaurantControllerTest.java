package workplate.workplateserver.restaurant.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import workplate.workplateserver.auth.domain.jwt.JwtTokenProvider;
import workplate.workplateserver.auth.domain.jwt.repository.JwtAccessTokenRepository;
import workplate.workplateserver.auth.domain.jwt.repository.JwtRefreshTokenRepository;
import workplate.workplateserver.auth.repository.MemberRepository;
import workplate.workplateserver.common.PageResponse;
import workplate.workplateserver.restaurant.domain.RestaurantType;
import workplate.workplateserver.restaurant.domain.dto.RestaurantDetailResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantMenuResponse;
import workplate.workplateserver.restaurant.domain.dto.RestaurantResponse;
import workplate.workplateserver.restaurant.service.RestaurantService;

/**
 * @author : parkjihyeok
 * @since : 2024/11/12
 */
@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@DisplayName("식당 Controller 테스트")
class RestaurantControllerTest {

	@Autowired
	MockMvc mockMvc;
	@MockBean
	RestaurantService restaurantService;

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
	@DisplayName("페이지 정보를 전달하면 페이징처리된 식당들이 나온다.")
	@WithMockUser
	void findAllByPage() throws Exception {
		// Given
		Pageable pageable = PageRequest.of(1, 3);
		RestaurantResponse r1 = new RestaurantResponse(1L, "가식당", RestaurantType.KOREAN_FOOD, "서울");
		RestaurantResponse r2 = new RestaurantResponse(2L, "나식당", RestaurantType.WESTERN_FOOD, "부산");
		RestaurantResponse r3 = new RestaurantResponse(3L, "다식당", RestaurantType.JAPANESE_FOOD, "울산");
		PageResponse<RestaurantResponse> response = new PageResponse<>(List.of(r1, r2, r3));
		given(restaurantService.findRestaurant(any())).willReturn(response);

		// When
		mockMvc.perform(get("/api/restaurants")
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
								fieldWithPath("data.content[].id").description("식당 ID값"),
								fieldWithPath("data.content[].name").description("식당 이름"),
								fieldWithPath("data.content[].restaurantType").description(
										"식당 종류 (KOREAN_FOOD, WESTERN_FOOD, JAPANESE_FOOD 등)"),
								fieldWithPath("data.content[].location").description("식당 위치 (서울, 부산, 울산 등)"),
								fieldWithPath("data.pageNum").description("현재 페이지 번호"),
								fieldWithPath("data.pageSize").description("페이지 당 항목 수"),
								fieldWithPath("data.totalPages").description("총 페이지 수"),
								fieldWithPath("data.totalElements").description("총 항목 수"),
								fieldWithPath("data.last").description("마지막 페이지 여부 (true일 경우 마지막 페이지)")

						)));

		// Then
	}

	@Test
	@DisplayName("식당 ID를 전달하면 식당의 상세정보를 반환한다.")
	@WithMockUser
	void findDetail() throws Exception {
		// Given
		RestaurantMenuResponse rm1 = new RestaurantMenuResponse("김밥", 1000);
		RestaurantMenuResponse rm2 = new RestaurantMenuResponse("제육볶음", 2000);
		RestaurantMenuResponse rm3 = new RestaurantMenuResponse("우동", 3000);
		RestaurantMenuResponse rm4 = new RestaurantMenuResponse("떡볶이", 4000);

		RestaurantDetailResponse response = new RestaurantDetailResponse("가식당", RestaurantType.KOREAN_FOOD, "서울",
				List.of(rm1, rm2, rm3, rm4));
		given(restaurantService.findRestaurantDetail(10L)).willReturn(response);

		// When
		mockMvc.perform(get("/api/restaurants/{id}", 10L))
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
								parameterWithName("id").description("식당 ID")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과"),
								fieldWithPath("data.name").description("식당 이름"),
								fieldWithPath("data.restaurantType").description(
										"식당 종류 (KOREAN_FOOD, WESTERN_FOOD, JAPANESE_FOOD 등)"),
								fieldWithPath("data.location").description("식당 위치 (예: 서울)"),
								fieldWithPath("data.menuList[].menuName").description("메뉴 이름"),
								fieldWithPath("data.menuList[].price").description("메뉴 가격 (원 단위)")
						)));

		// Then
	}

	@Test
	@DisplayName("잘못된 식당 ID를 전달하면 400을 반환한다.")
	@WithMockUser
	void findDetailFail() throws Exception {
		// Given
		doThrow(new IllegalArgumentException("해당 식당을 찾을 수 없습니다.")).when(restaurantService).findRestaurantDetail(10L);

		// When
		mockMvc.perform(get("/api/restaurants/{id}", 10L))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.message").value("요청에 실패했습니다."))
				.andExpect(jsonPath("$.data").exists())
				.andDo(print())
				.andDo(document("{class-name}/{method-name}/",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						pathParameters(
								parameterWithName("id").description("식당 ID")
						),
						responseFields(
								fieldWithPath("success").description("성공여부"),
								fieldWithPath("message").description("응답 메시지"),
								fieldWithPath("data").description("처리 결과")
						)));

		// Then
	}
}
