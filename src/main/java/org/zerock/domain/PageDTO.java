package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	// 필드
	private int startPage; // 시작 페이지
	private int endPage; // 끝 페이지
	private boolean prev, next; // 이전, 다음
	
	private int total; // 게시글(객체) 총 개수
	private Criteria cri; // 페이지번호, 한 페이지당 게시글(객체) 출력 개수
	
	// 생성자
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10; // cri.getPageNum() = 1, 1/10.0 = 0.1 * 10, endPage = 10 / cri.getPageNum() = 2, 2/10.0 = 0.2 * 10, endPage = 20
		this.startPage = this.endPage - 9; // endPage = 10, 10-9 = 1, startPage = 1 / endPage = 20, 20-9 = 11, startPage = 11 -> 결과 1~10페이지 보여줌 / 11~20페이지 보여줌
		
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount())); // total = 80 -> 80 * 1.0 / 10 = 8 -> readEnd = 8 (마지막 페이지 번호가 되어야 할 숫자)
		// 게시글이 80개라면 endPage는 8이 되어야한다.
		// endPage는 위의 코드들로 인해 자동으로 10단위씩 끊기기 때문에 마지막페이지번호를 구하기 위해 realEnd 변수를 이용해 다시 구한다.
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd; // 마지막 페이지 번호가 되어야 할 숫자를 endPage에 넣어서 변경 (10 -> 8)
		}
		
		this.prev = this.startPage > 1; // 시작 페이지가 1이상일때 (2부터) 이전페이지로 이동 기능 활성화
		
		this.next = this.endPage < realEnd; // ex) 31~40페이지 출력시 realEnd가 80페이지까지 있다면 endPage(40) < realEnd(80)이기 때문에 다음페이지로 이동 기능 활성화
	}
}
