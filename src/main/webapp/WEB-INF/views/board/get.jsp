<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>


<div class="row">
  <div class="col-lg-12">
    <h1 class="page-header">Board Read</h1>
  </div>
  <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
  <div class="col-lg-12">
   
    <div class="panel panel-default">
      <div class="panel-heading">Board Read Page</div>
      <!-- /.panel-heading -->
      <div class="panel-body">

          <div class="form-group">
          <label>Bno</label> <input class="form-control" name='bno'
            value='<c:out value="${board.bno }"/>' readonly="readonly">
        </div>

        <div class="form-group">
          <label>Title</label> <input class="form-control" name='title'
            value='<c:out value="${board.title }"/>' readonly="readonly">
        </div>

        <div class="form-group">
          <label>Text area</label>
          <textarea class="form-control" rows="3" name='content'
            readonly="readonly"><c:out value="${board.content}" /></textarea>
        </div>

        <div class="form-group">
          <label>Writer</label> <input class="form-control" name='writer'
            value='<c:out value="${board.writer }"/>' readonly="readonly">
        </div>

<%-- 		<button data-oper='modify' class="btn btn-default">
        <a href="/board/modify?bno=<c:out value="${board.bno}"/>">Modify</a></button>
        <button data-oper='list' class="btn btn-info">
        <a href="/board/list">List</a></button> --%>


<button data-oper='modify' class="btn btn-default">Modify</button>
<button data-oper='list' class="btn btn-info">List</button>

<%-- <form id='operForm' action="/boad/modify" method="get">
  <input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
</form> --%>


<form id='operForm' action="/boad/modify" method="get">
  <input type='hidden' id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
  <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
  <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
  <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
  <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>  
 
</form>



      </div>
      <!--  end panel-body -->

    </div>
    <!--  end panel-body -->
  </div>
  <!-- end panel -->
</div>
<!-- /.row -->

<div class='row'>
	<div class="col-lg-12">
	
		<!-- /.panel -->
		<div class="panel panel-default">
			<!-- <div class="panel-heading">
       			<i class="fa fa-comments fa-fw"></i> 댓글 목록
      		</div> -->
      		
      		<div class="panel-heading">
       			<i class="fa fa-comments fa-fw"></i> 댓글 목록
       			<button id='addReplyBtn' class='btn-primary btn-xs pull-right'>댓글 달기</button>
      		</div>
      		<!-- /.panel-heading" -->
      		
      		<div class="panel-body">
      			<ul class="chat">
      				<!-- reply(댓글) 시작 -->
      				<li class="left clearfix" data-rno='12'>
      					<div>
      						<div class="header">
      							<strong class="primary-font">user00</strong>
      							<small class="pull-right text-muted">2018-01-01 13:13</small>
      						</div>
      						<!-- /.header -->
      						<p>Good job!</p>
      					</div>
      				</li>
      			</ul>
      		</div>
      		<!-- /.panel-body -->
      		<div class="panel-footer">
      		
      		</div>
      		<!-- /.panel-footer -->
		</div>
		<!-- /.panel panel-default -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                	<label>Reply</label>
                	<input class="form-control" name='reply' value='New Reply!!!'>
                </div>
                <div class="form-group">
                	<label>Replyer</label>
                	<input class="form-control" name='replyer' value='replyer'>
                </div>
                <div class="form-group">
                	<label>Reply Date</label>
                	<input class="form-control" name='replyDate' value=''>
                </div>
            </div>
            <div class="modal-footer">
                <button id='modalModBtn' type="button" class="btn btn-warning">수정</button>
                <button id='modalRemoveBtn' type="button" class="btn btn-danger">삭제</button>
                <button id='modalRegisterBtn' type="button" class="btn btn-primary">등록</button>
                <button id='modalCloseBtn' type="button" class="btn btn-default">닫기</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script type="text/javascript" src="/resources/js/reply.js"> /* 외부파일 incloude용 */</script>

<script>
$(document).ready(function(){
	
	var bnoValue = '<c:out value="${board.bno}"/>';
	var replyUL = $(".chat");
	
		showList(1);
		
		function showList(page){
			
			console.log("show list " + page);
			
			replyService.getList({bno:bnoValue, page:page||1}, function(replyCnt, list){
				
				console.log("replyCnt : " + replyCnt);
				console.log("list : " + list);
				console.log(list);
				
				if(page == -1){ // 페이지 번호가 -1로 전달되면 마지막페이지를 호출
					pageNum = Math.ceil(replyCnt/10.0); // 댓글 개수를 10으로 나눠서 올림을 함
					showList(pageNum);
					return;
				}
				
				var str = "";
				if(list == null || list.length == 0){
					//replyUL.html("");
					
					return;
				}
				for (var i = 0, len = list.length || 0; i < len; i++){
					str +="<li class='left clearfix' data-rno='"+list[i].rno+"'>";
					str +="	<div><div class='header'><strong class='primary-font'>["+list[i].rno+"] "+list[i].replyer+"</strong>";
					str +="		<small class='pull-right text-muted'>"+replyService.displayTime(list[i].replyDate)+"</small></div>";
					str +="		<p>"+list[i].reply+"</p></div></li>";
				} // for 종료
				replyUL.html(str);
				
				showReplyPage(replyCnt);
			}); // replyService.getList 종료
		} // function showList 종료
		
		// 댓글 페이지번호 출력
		var pageNum = 1;
		var replyPageFooter = $(".panel-footer");
		
		function showReplyPage(replyCnt){
			
			var endNum = Math.ceil(pageNum / 10.0) * 10;
			var startNum = endNum - 9;
			
			var prev = startNum != 1;
			var next = false;
			
			if(endNum * 10 >= replyCnt){
				endNum = Math.ceil(replyCnt/10.0);
			}
			
			if(endNum * 10 < replyCnt){
				next = true;
			}
			
			var str = "<ul class='pagination pull-right'>";
			
			if(prev){
				str+= "<li class='page-item'><a class='page-link' href='"+(startNum -1)+"'>Previous</a></li>";
			}
			
			for(var i = startNum ; i <= endNum; i++){
				
				var active = pageNum == i? "active":"";
				
				str+= "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
			}
			
			if(next){
				str+= "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li>";
			}
			
			str += "</ul></div>";
			
			console.log(str);
			replyPageFooter.html(str);
		}
		
		replyPageFooter.on("click", "li a", function(e){
			e.preventDefault();
			console.log("page click");
			
			var targetPageNum = $(this).attr("href");
			
			console.log("targetPageNum : " + targetPageNum);
			
			pageNum = targetPageNum;
			
			showList(pageNum);
		});
		
		// 모달
		var modal = $(".modal");
		var modalInputReply = modal.find("input[name='reply']");
		var modalInputReplyer = modal.find("input[name='replyer']");
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
		
		$("#addReplyBtn").on("click", function(e){
			
			modal.find("input").val("");
			modalInputReplyDate.closest("div").hide(); // 모달창에서 날짜입력부분 지우기
			modal.find("button[id !='modalCloseBtn']").hide(); // 모달창에서 수정, 삭제, 등록 버튼 지우기 (button[id !='modalCloseBtn -> 닫기버튼이 아닌것)
			
			modalRegisterBtn.show(); // 등록버튼만 다시 보여주기 (결과적으로 등록, 닫기 버튼 2개만 나옴)
			
			$(".modal").modal("show"); // 모달창 실행
			
		});
		
		// 댓글 추가 기능
		modalRegisterBtn.on("click",function(e){
			
			var reply = {
					reply : modalInputReply.val(),
					replyer : modalInputReplyer.val(),
					bno : bnoValue
			};
			replyService.add(reply, function(result){
				
				alert(result);
				
				modal.find("input").val("");
				modal.modal("hide");
				
				//showList(1);
				showList(-1); // 새로운 댓글 추가시 상단의 전체 댓글 숫자 파악 코드 호출하여 마지막 페이지 이동
			});
		});
		
		//댓글 조회 클릭 이벤트 처리 (댓글 클릭시 수정, 삭제할 수 있게 모달창 띄움)
		$(".chat").on("click", "li", function(e){
			
			var rno = $(this).data("rno"); // 해당 댓글의 댓글번호를 변수에 지정
			
			replyService.get(rno, function(reply){ 
				
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate))
				.attr("readonly", "readonly");
				modal.data("rno", reply.rno); // 댓글 번호에 해당하는 객체를 가져옴 (날짜는 읽기전용으로 수정불가 readonly)
				
				modal.find("button[id != 'modalCloseBtn']").hide(); // 닫기 버튼 제외 숨김 
				modalModBtn.show(); // 수정버튼 보여줌
				modalRemoveBtn.show(); // 삭제버튼 보여줌 (결론적으로는 등록버튼만 숨김)
				
				$(".modal").modal("show");
				
			});
		});
		
		// 댓글 수정 기능
		modalModBtn.on("click", function(e){
			
			var reply = {rno:modal.data("rno"), reply:modalInputReply.val()}; // 댓글번호, 수정하는 댓글 내용
			
			replyService.update(reply, function(result){ // replyService.update로 전달하여 객체 수정 후 저장
				
				alert(result); // 결과 success alert창으로 보여줌
				modal.modal("hide");
				showList(pageNum); // 처리 후 페이지 갱신
			});
		});
		
		// 댓글 삭제 기능
		modalRemoveBtn.on("click", function(e){
			
			var rno = modal.data("rno"); // 댓글번호
			
			replyService.remove(rno, function(result){ // replyService.remove로 전달하여 객체 삭제
				
				alert(result); // 결과 success alert창으로 보여줌
				modal.modal("hide");
				showList(pageNum);
			});
		});
		
});

</script>


<script> 
	
	// 댓글 추가
	/* console.log("-----------------------");
	console.log("reply.js에 add 함수 실행");
	
	var bnoValue = '<c:out value="${board.bno}"/>'; */ // 게시물 상세 보기에 있는 게시물 번호를 변수에 넣음
	
	/* replyService.add(
		{reply:"자바스크립트 테스트", replyer:"ajax", bno:bnoValue}, // json으로 더미데이터 생성
		function(result){
			alert("결과 : " + result);
		}
	); */
	
	// 해당 게시물의 모든 댓글 가져오기
	/* console.log("-----------------------");
	console.log("reply.js에 getList 함수 실행");
	
	var bnoValue = '<c:out value="${board.bno}"/>';
	
	replyService.getList({bno:bnoValue, page:1}, function(list){
		
		for(var i = 0, len = list.length||0; i < len; i++){
			console.log(list[i]);
		}
	}); */
	
	// 댓글 삭제 테스트
	/* replyService.remove(28, function(count){
		
		console.log(count);
		
		if(count === "success"){
			alert("삭제 완료");
		}
	}, function(err){
		alert('에러 발생');
	
	}); */
	
	// 댓글 수정 테스트
	/* replyService.update({
		rno : 27,
		bno : bnoValue,
		reply : "프론트로 댓글 수정"
	}, function(result){
		alert("수정 완료");
		
	}); */
	
	// 댓글 조회 테스트
	/* replyService.get(10, function(data){
		console.log(data);
		// {rno: 10, bno: 8, reply: '수정한 댓글', replyer: 'kkw', replyDate: 1724723545000, …}
	}); */
	
	
</script>

<script type="text/javascript">
$(document).ready(function() {
  
  var operForm = $("#operForm"); 
  
  $("button[data-oper='modify']").on("click", function(e){
    
    operForm.attr("action","/board/modify").submit();
    
  });
  
    
  $("button[data-oper='list']").on("click", function(e){
    
    operForm.find("#bno").remove();
    operForm.attr("action","/board/list")
    operForm.submit();
    
  });  
});
</script>


<%@include file="../includes/footer.jsp"%>
