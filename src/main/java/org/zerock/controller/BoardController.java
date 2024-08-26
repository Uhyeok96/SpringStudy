package org.zerock.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller  	//스프링이 컨트롤러 역할을 제공
@Log4j2
@RequestMapping("/board/*") 	// http://localhost:80/board/??????
@AllArgsConstructor		// 모든 필드값으로 생성자 만듬
public class BoardController {

	//필드
	private BoardService service;  // BoardController boardController = new BoardController(BoardService); 
	
//	@GetMapping("/list")		//  http://localhost:80/board/list
//	public void list(Model model) {	//import org.springframework.ui.Model 스프링이 관리하는 메모리
//		
//		log.info("BoardController.list메서드 실행");
//		model.addAttribute("list", service.getlist()); // name : list , Object : List<BoardVO> 
//		
//	} 리스트 페이징 출력으로 사용안함
	
	// 리스트 페이징 출력
	@GetMapping("/list")		//  http://localhost:80/board/list
	public void list(Criteria cri, Model model) { //import org.springframework.ui.Model 스프링이 관리하는 메모리
		
		log.info("BoardController.list메서드 실행 : " + cri);
		model.addAttribute("list", service.getList(cri)); // name : list , Object : List<BoardVO>
		// model.addAttribute("pageMaker", new PageDTO(cri, 123)); // new PageDTO(cri, 123) = (한페이지당 10개의 객체 출력, 총 객체 수 123개 임의의 값)
		
		int total = service.getTotal(cri);
		
		log.info("total : " + total);
		// INFO  org.zerock.controller.BoardController(list48) - total : 14846
		
		model.addAttribute("pageMaker", new PageDTO(cri, total)); // 총 게시물 수를 구해 total 변수로 처리 (위 임시코드 사용 안함)
	}
	
	@GetMapping("/register") //  http://localhost:80/board/register
	public void register() {
		log.info("BoardController.register get메서드 실행");
		
		// 리턴이 void -> url과 같은 jsp를 찾는다. http://localhost:80/board/register.jsp
	}
	
	@PostMapping("/register") //  http://localhost:80/board/register
	public String register(BoardVO board, RedirectAttributes rttr) {
		// RedirectAttributes rttr -> 1회성을 같는 값을 제공 -> addFlashAttribute("이름","값");
		
		log.info("BoardController.register post메서드 실행");
		service.register(board); // 프론트에서 form 값이 객체로 넘어옴
		
		rttr.addFlashAttribute("result", board.getBno()); // 객체에 있는 bno값을 1회성으로 가지고 있음(model영역)
		
		return "redirect:/board/list"; // response.sendRedirect()
		// 등록후에는 list페이지로 보냄 http://localhost:80/board/list
		
	}
	
	
	@GetMapping({"/get", "/modify"})
	// 이중화 작업 : http://localhost:80/board/get -> board/get.jsp
	// 이중화 작업 : http://localhost:80/board/modify -> board/modify.jsp
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		// @ModelAttribute -> 자동으로 Model에 지정한 이름으로 데이터를 담는다.
				
		log.info("BoardController.get메서드 실행");
		model.addAttribute("board", service.get(bno));
		// 서비스계층에 get메서드에 bno 값을 넣어 주면 객체(sql처리후)가 나옴
		
	}
	
	
	@PostMapping("/modify") // http://localhost:80/board/modify
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info("BoardController.modify메서드 실행");
		
		if (service.modify(board)) { // service.modify의 리턴 타입이 boolean
			rttr.addFlashAttribute("result", "success"); // 수정 성공시 success 메시지를 보냄
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword()); 
		// Criteria클래스의 getListLink()메서드 UriComponentsBuilder 사용으로 간단히 정리 가능
		// + cri.getListLink()로 모두 포함 가능
		
		return "redirect:/board/list" + cri.getListLink();  // 결론 http://localhost:80/board/list
	}
	
	
	
	@PostMapping("/remove") 	// 번호를 받아 delete 쿼리를 실행
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr){
		
		log.info("BoardController.remove메서드 실행");
		if (service.remove(bno)) { // service.remove의 리턴 타입이 boolean
			rttr.addFlashAttribute("result", "success"); // 수정 성공시 success 메시지를 보냄
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		// Criteria클래스의 getListLink()메서드 UriComponentsBuilder 사용으로 간단히 정리 가능
		// + cri.getListLink()로 모두 포함 가능
		
		
		return "redirect:/board/list" + cri.getListLink();  // 결론 http://localhost:80/board/list
	}
	
	
	
	
	
	
	
	
}
