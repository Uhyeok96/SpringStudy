package org.zerock.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.service.BoardService;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController // Rest로 응답 함!!! -> view-jsp가 아닌 json, xml로 나옴
@RequestMapping("/replies") // http://localhost:80/replies/???
@Log4j2
@AllArgsConstructor // new ReplyController(ReplyService);
public class ReplyController { // Rest 방식의 컨트롤러로 구현 + ajax 처리 함
	
	//private BoardService bService;
	private ReplyService service;
	
	// http://localhost:80/replies/new (json으로 입력 되면 객체로 저장됨)
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})// 입력값은 json 으로
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		// 리턴은 200 | 500 으로 처리 된다.
		
		log.info("ReplyVO 객체 입력 값 : " + vo); // 파라미터로 넘어온 값 출력 테스트
		
		int insertCount = service.register(vo); // sql 처리 후에 결과값이 1 | 0 이 나옴
		
		log.info("서비스+매퍼 처리 결과 : " + insertCount);
		
		
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) // 200 정상
								: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 서버 오류
		// 삼항 연산자 처리
	}
	
	// http://localhost:80/replies/pages/11/1 -> xml
	// http://localhost:80/replies/pages/11/1.json -> json
//	@GetMapping(value="/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
//		
//		log.info("ReplyController.getList()메서드 실행");
//		log.info("페이지 번호 : " + page);
//		log.info("찾을 번호 : " + bno);
//		
//		Criteria cri = new Criteria(page, 10); // 현재 페이지와 리스트 개수를 전달
//		
//		log.info("Criteria : " + cri);
//		
//		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK); // 200 정상
//		// [{"rno":7,"bno":11,"reply":"댓글11","replyer":"kkw","replyDate":1724723545000,"updateDate":1724723545000},
//		// {"rno":16,"bno":11,"reply":"댓글11","replyer":"kkw","replyDate":1724723572000,"updateDate":1724723572000},
//		// {"rno":27,"bno":11,"reply":"매퍼댓글테스트","replyer":"매퍼kkw","replyDate":1724724694000,"updateDate":1724724694000}]
//	}
	
	// http://localhost:80/replies/4
	@GetMapping(value="/{rno}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		
		log.info("ReplyController.get()메서드 실행 / 찾을 rno : " + rno);
		
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK); // 200 정상
	}
	
	@DeleteMapping(value="/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE}) // JSON으로 나올 필요가 없음
	public ResponseEntity<String> remove(@PathVariable("rno") Long rno){
		
		log.info("ReplyController.remove()메서드 실행 / 삭제할 rno : " + rno);
		
		return service.remove(rno) == 1 
				? new ResponseEntity<>("success", HttpStatus.OK) // 200 정상
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 서버 오류
	}
	
	// http://localhost:80/replies/3
	// RequestMethod.PUT -> @PutMapping (객체 전체 필드를 수정한다)
	// RequestMethod.PATCH -> @PatchMapping (객체의 일부 필드(부분) 수정한다)
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, 
			value = "/{rno}",
			consumes = "application/json", 
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		//								이미 폼(form)에 있는 값				수정할 번호
		
		vo.setRno(rno); // 이미 가지고 있는 객체에 rno값을 넣음
		
		log.info("ReplyController.modify()메서드 실행 / 수정할 rno : " + rno);
		
		log.info("수정할 객체 : " + vo);
		
		return service.modify(vo) == 1 
				? new ResponseEntity<>("success", HttpStatus.OK) // 200 정상
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 서버 오류;
	}
	
	// http://localhost:80/replies/pages/11/1 -> xml
	// http://localhost:80/replies/pages/11/1.json -> json
	@GetMapping(value="/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") Long bno){
		
		log.info("ReplyController.getList()메서드 실행");
		log.info("페이지 번호 : " + page);
		log.info("찾을 번호 : " + bno);
		
		Criteria cri = new Criteria(page, 10); // 현재 페이지와 리스트 개수를 전달
		
		log.info("get Reply List bno : " + bno);
		
		log.info("Criteria : " + cri);
		
		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK); // 200 정상
//		{"replyCnt":33,"list":[{"rno":7,"bno":11,"reply":"댓글1111","replyer":"kkw","replyDate":1724723545000,"updateDate":1724832980000},
//		                       {"rno":16,"bno":11,"reply":"댓글11","replyer":"kkw","replyDate":1724723572000,"updateDate":1724832995000},
//		                       {"rno":27,"bno":11,"reply":"프론트로 댓글 수정","replyer":"매퍼kkw","replyDate":1724724694000,"updateDate":1724827756000},
//		                       {"rno":30,"bno":11,"reply":"매퍼댓글테스트","replyer":"매퍼kkw","replyDate":1724748739000,"updateDate":1724748739000},
//		                       {"rno":33,"bno":11,"reply":"자바스크립트 테스트11","replyer":"ajax","replyDate":1724811036000,"updateDate":1724833160000},
//		                       {"rno":34,"bno":11,"reply":"자바스크립트 테스트","replyer":"ajax","replyDate":1724815164000,"updateDate":1724815164000},
//		                       {"rno":35,"bno":11,"reply":"자바스크립트 테스트","replyer":"ajax","replyDate":1724816252000,"updateDate":1724816252000},
//		                       {"rno":37,"bno":11,"reply":"자바스크립트 테스트","replyer":"ajax","replyDate":1724817326000,"updateDate":1724817326000},
//		                       {"rno":38,"bno":11,"reply":"자바스크립트 테스트","replyer":"ajax","replyDate":1724817401000,"updateDate":1724817401000},
//		                       {"rno":39,"bno":11,"reply":"자바스크립트 테스트","replyer":"ajax","replyDate":1724817426000,"updateDate":1724817426000}]}
		
	}
	
}
