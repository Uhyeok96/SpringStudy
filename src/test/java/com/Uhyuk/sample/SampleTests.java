package com.Uhyuk.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

// 필수 3가지 -> pom.xml에 spring-test 코드 필수 (36행)
@RunWith(SpringJUnit4ClassRunner.class) // 메서드 별로 테스트
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") // 참조할 파일
@Log4j2 // log 출력용
public class SampleTests {
	
	@Setter(onMethod_ = @Autowired) // setRestaurant(Restaurant)
	private Restaurant restaurant;  // Restaurant restaurant = new Restaurant(restaurant);
	
	@Test // import org.junit.Test;  메서드 별로 테스트가 진행 됨(메서드명 블럭 -> 우클릭 -> run as -> junit)
	public void testExist() {
		
		assertNotNull(restaurant); // assertNotNull() 
		
		log.info(restaurant);
		log.info("------------");
		log.info(restaurant.getChef()); // restaurant 객체에 있는 Chef 필드를 가져와 출력
		
		// INFO  com.Uhyuk.sample.SampleTests(testExist28) - Restaurant(chef=Chef(name=null, age=0, regDate=null), restaurantName=null, openTime=null, closeTime=null)
		// INFO  com.Uhyuk.sample.SampleTests(testExist29) - ------------
		// INFO  com.Uhyuk.sample.SampleTests(testExist30) - Chef(name=null, age=0, regDate=null)
		
	}
}
