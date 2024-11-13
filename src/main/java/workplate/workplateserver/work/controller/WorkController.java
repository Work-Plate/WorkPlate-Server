package workplate.workplateserver.work.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import workplate.workplateserver.common.ApiResponse;
import workplate.workplateserver.common.PageResponse;
import workplate.workplateserver.work.domain.dto.WorkJoinRequest;
import workplate.workplateserver.work.domain.dto.WorkResponse;
import workplate.workplateserver.work.service.WorkService;

/**
 * 소일거리 Controller
 *
 * @author : parkjihyeok
 * @since : 2024/11/13
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkController {

	private final WorkService workService;

	@GetMapping("/works")
	public ApiResponse<PageResponse<WorkResponse>> findAll(Pageable pageable) {
		PageResponse<WorkResponse> response = workService.findAll(pageable);
		return ApiResponse.success(response);
	}

	@GetMapping("/works/{id}")
	public ApiResponse<WorkResponse> findById(@PathVariable Long id) {
		WorkResponse response = workService.findById(id);
		return ApiResponse.success(response);
	}

	@PostMapping("/works-join")
	public ApiResponse<String> joinWork(WorkJoinRequest request) {
		workService.joinWork(request.getUsername(), request.getWorkId());
		return ApiResponse.success("소일거리 참가에 성공하였습니다.");
	}
}
