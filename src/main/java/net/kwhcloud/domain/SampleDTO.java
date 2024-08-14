package net.kwhcloud.domain; // 스프링에서는 dto를 domain에 묶어서 관리함

import lombok.Data;

@Data // lombok.Data (toString, getter, setter, equals, 생성자 등을 자동으로 생성)
public class SampleDTO {
	
	// 필드
	private String name;
	private int age;
	
	// 생성자
	
	
	// 메서드
	
	
}
