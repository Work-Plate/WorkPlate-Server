package workplate.workplateserver.common;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

/**
 * 페이징 처리를 담은 응답 객체
 *
 * @author : parkjihyeok
 * @since : 2024/11/10
 */
@Getter
public class PageResponse<T> {

	private final List<T> content;
	private int pageNum; // 현재 페이지
	private int pageSize; // 페이징 처리 크기
	private int totalPages; // 전체 페이지
	private long totalElements; // 전체 데이터 수
	private boolean isLast; // 마지막 페이지 여부

	public PageResponse(List<T> content) {
		this.content = content;
	}

	public void setPageInfo(Page<?> pageInfo) {
		this.pageNum = pageInfo.getNumber();
		this.pageSize = pageInfo.getSize();
		this.totalPages = pageInfo.getTotalPages();
		this.totalElements = pageInfo.getTotalElements();
		this.isLast = pageInfo.isLast();
	}
}
