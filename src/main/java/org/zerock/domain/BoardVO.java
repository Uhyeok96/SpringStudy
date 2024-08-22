package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data	// 게터, 세터, toString, equal, 생성자 등을 관여한다.
public class BoardVO {

	private Long bno ;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	
}
