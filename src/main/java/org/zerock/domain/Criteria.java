package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum; // 페이지 번호
	private int amount; // 데이터 개수
	
	private String type; // 검색 타입
	private String keyword; // 검색어 키워드
	
	public Criteria() {
		this(1, 10); // 페이지번호 기본값 1페이지, 한 페이지당 10개씩 데이터 출력
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() { // 검색기능을 위해 추가
		// 검색 조건을 배열로 만들어서 한 번에 처리함
		
		return type == null? new String[] {}: type.split("");
	}
	
	public String getListLink() { 
		// UriComponentsBuilder는 여러개의 파라미터들을 연결해서 URL의 형태로 만들어줌
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
			.queryParam("pageNum", this.pageNum)
			.queryParam("amount", this.getAmount())
			.queryParam("type", this.getType())
			.queryParam("keyword", this.getKeyword()); // queryParam -> 파라미터 추가
		
		return builder.toUriString();
	}
}
