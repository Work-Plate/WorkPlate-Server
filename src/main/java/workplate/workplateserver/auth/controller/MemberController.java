package workplate.workplateserver.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.auth.domain.dto.request.JoinRequest;
import workplate.workplateserver.auth.service.MemberService;
import workplate.workplateserver.common.ApiResponse;

/**
 * 회원 Controller
 *
 * @author : parkjihyeok
 * @since : 2024/10/31
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/join")
	public ResponseEntity<ApiResponse<String>> join(@RequestBody JoinRequest joinRequest) {
		memberService.saveMember(joinRequest);

		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.success("회원가입에 성공하였습니다"));
	}

	// @PostMapping("/member")
	// public ResponseEntity<ApiResponse<String>> addMemberDetails(@RequestBody JoinRequest joinRequest) {
	// 	memberService.saveMember(joinRequest);
	//
	// 	return ResponseEntity.status(HttpStatus.OK)
	// 			.body(ApiResponse.success("회원의 상세정보를 입력하였습니다"));
	// }
}
