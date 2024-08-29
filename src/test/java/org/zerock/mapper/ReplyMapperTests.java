package org.zerock.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
// Java Config
// @ContextConfiguration(classes = {org.zerock.config.RootConfig.class} )
@Log4j2
public class ReplyMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	@Test
	public void testMapper() {
		
		log.info(mapper);
		// INFO  org.zerock.mapper.ReplyMapperTests(testMapper30) - org.apache.ibatis.binding.MapperProxy@4d27d9d
	}
	
	@Test
	public void testCreate() {
		
		ReplyVO vo = new ReplyVO();
		
		vo.setBno(11L);
		vo.setReply("매퍼댓글테스트");
		vo.setReplyer("매퍼kkw");
		
		mapper.insert(vo);
	}
	
	@Test
	public void testRead() {
		
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info("select + rno : " + vo);
		// INFO  org.zerock.mapper.ReplyMapperTests(testRead54) - select + rno : ReplyVO(rno=10, bno=8, reply=댓글8, replyer=kkw, replyDate=Tue Aug 27 10:52:25 KST 2024, updateDate=Tue Aug 27 10:52:25 KST 2024)
	}
	
	@Test
	public void testUpdate() {
		
		Long targetRno = 10L;
		
		ReplyVO vo = mapper.read(targetRno); // 10번 객체를 가져와!!!
		
		vo.setReply("매퍼로 수정 테스트");
		
		int count = mapper.update(vo); // 수정된 객체를 넣고 결과를 받음
		
		log.info("수정된 카운트 : " + count);
		// INFO  org.zerock.mapper.ReplyMapperTests(testUpdate69) - 수정된 카운트 : 1
		log.info("수정된 객체 : " + vo);
		// INFO  org.zerock.mapper.ReplyMapperTests(testUpdate71) - 수정된 객체 : ReplyVO(rno=10, bno=8, reply=매퍼로 수정 테스트, replyer=kkw, replyDate=Tue Aug 27 10:52:25 KST 2024, updateDate=Tue Aug 27 12:06:18 KST 2024)
	}
	
	@Test
	public void testDelete() {
		
		Long targetRno = 1L;
		
		log.info("삭제 후 결과 : " + mapper.delete(targetRno));
		// INFO  org.zerock.mapper.ReplyMapperTests(testDelete63) - 삭제 후 결과 : 1
	}
	
	// 번호를 이용한 객체가 리스트로 나옴
	@Test
	public void testList() {
		
		Criteria cri = new Criteria(); // 빈객체 생성
		
		log.info("Criteria : " + cri);
		// INFO  org.zerock.mapper.ReplyMapperTests(testList90) - Criteria : Criteria(pageNum=1, amount=10, type=null, keyword=null)
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 10L);
		
		replies.forEach(reply -> log.info(reply));
		// INFO  org.zerock.mapper.ReplyMapperTests(lambda$094) - ReplyVO(rno=2, bno=10, reply=댓글10, replyer=kkw, replyDate=Tue Aug 27 10:52:16 KST 2024, updateDate=Tue Aug 27 10:52:16 KST 2024)
		// INFO  org.zerock.mapper.ReplyMapperTests(lambda$094) - ReplyVO(rno=8, bno=10, reply=댓글10, replyer=kkw, replyDate=Tue Aug 27 10:52:25 KST 2024, updateDate=Tue Aug 27 10:52:25 KST 2024)
		// INFO  org.zerock.mapper.ReplyMapperTests(lambda$094) - ReplyVO(rno=13, bno=10, reply=댓글10, replyer=kkw, replyDate=Tue Aug 27 10:52:39 KST 2024, updateDate=Tue Aug 27 10:52:39 KST 2024)
		// INFO  org.zerock.mapper.ReplyMapperTests(lambda$094) - ReplyVO(rno=14, bno=10, reply=댓글10, replyer=kkw, replyDate=Tue Aug 27 10:52:41 KST 2024, updateDate=Tue Aug 27 10:52:41 KST 2024)
	}
	
	// json을 적용한 테스트
	@Test
	public void testList2() {
		
		Criteria cri = new Criteria(2, 10); // 2페이지에 해당하는 10개의 댓글 출력
		
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 11L); // 11번 게시물 테스트
		
		replies.forEach(reply -> log.info(reply));
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=40, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 12:57:22 KST 2024, updateDate=Wed Aug 28 12:57:22 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=41, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 12:57:41 KST 2024, updateDate=Wed Aug 28 12:57:41 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=42, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 13:15:06 KST 2024, updateDate=Wed Aug 28 13:15:06 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=43, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 13:15:45 KST 2024, updateDate=Wed Aug 28 13:15:45 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=45, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 13:17:54 KST 2024, updateDate=Wed Aug 28 13:17:54 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=46, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 13:20:08 KST 2024, updateDate=Wed Aug 28 13:20:08 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=47, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 14:14:23 KST 2024, updateDate=Wed Aug 28 14:14:23 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=48, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 14:15:36 KST 2024, updateDate=Wed Aug 28 14:15:36 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=49, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 14:18:13 KST 2024, updateDate=Wed Aug 28 14:18:13 KST 2024)
//		INFO  org.zerock.mapper.ReplyMapperTests(lambda$1110) - ReplyVO(rno=50, bno=11, reply=자바스크립트 테스트, replyer=ajax, replyDate=Wed Aug 28 14:18:40 KST 2024, updateDate=Wed Aug 28 14:18:40 KST 2024)
	}
	

}
