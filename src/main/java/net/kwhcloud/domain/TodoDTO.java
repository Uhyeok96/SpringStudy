package net.kwhcloud.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoDTO { // Todo는 일과표
	
	private String title;	// 제목(할일)
	
	@DateTimeFormat(pattern = "yyyy/MM/dd") // @InitBinder 사용 안함
	private Date dueDate;	// 적용 시간
	private boolean check; 	// 성공 여부
	
}
