/**
 * 댓글 ajax 처리용 javaScript 파일 입니다.
 */
 
 
 console.log("댓글용 모듈 실행 중.....");
 
 var replyService = (function(){
	
					function add(reply, callback, error){ // 외부에서 replyService.add(객체, 콜백)을 전달하는 형태
						console.log("댓글추가용 함수.....");
						
						$.ajax({
							type : 'post',					// @PostMapping
							url : '/replies/new',			// http://localhost:80/replies/new
							data : JSON.stringify(reply),	// Json으로 받아 객체로 넘김 (stringify)
							contentType : "application/json; charset=utf-8",
							
							success : function(result, status, xhr){ // 위 코드 성공시 함수
								// result : 결과
								// status : 200 | 500
								// xhr : xmlHttpRequest 객체(서블릿에서 요청 객체와 유사함.)
								if(callback){ // callback = true 면 아래 코드 실행
									callback(result);
								} // 콜백 if 종료
							}, // success 종료
							
							error : function(xhr, status, er){
								if(error){
									error(er);
								} // 에러 if 종료
							} // error 종료
						}) // ajax 종료
					} // function add 종료
					
					function getList(param, callback, error){
						
						var bno = param.bno;
						var page = param.page || 1;
						
						$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
							function(data){
								if (callback){
									// callback(data); 댓글 목록만 가져오는 경우
									callback(data.replyCnt, data.list); // 댓글 숫자와 목록을 가져오는 경우
								} // 콜백 if 종료
							}).fail(function(xhr, status, err){
								if(error){
									error();
								} // 에러 if 종료
							}); // fail 종료
					} // function getList 종료
					
					function remove(rno, callback, error){
						$.ajax({
							type : 'delete',				// @DeleteMapping
							url : '/replies/' + rno,		// http://localhost:80/replies/댓글번호
							success : function(deleteResult, status, xhr){ // 위 코드 성공시 함수
							// result : 결과
							// status : 200 | 500
							// xhr : xmlHttpRequest 객체(서블릿에서 요청 객체와 유사함.)
								if(callback){
									callback(deleteResult);
								} // 콜백 if 종료
							}, // success 종료
							error : function(xhr, status, er){
								if(error){
									error(er);
								} // 에러 if 종료
							} // error 종료
						}) // ajax 종료
					} // function remove 종료
					
					function update(reply, callback, error){
						
						console.log("RNO : " + reply.rno);
						
						$.ajax({
							type : 'put',					// @PutMapping
							url : '/replies/' + reply.rno,	// http://localhost:80/replies/댓글번호
							data  : JSON.stringify(reply),	// Json으로 받아 객체로 넘김 (stringify)
							contentType : "application/json; charset=utf-8",
							success : function(result, status, xhr){
							// result : 결과
							// status : 200 | 500
							// xhr : xmlHttpRequest 객체(서블릿에서 요청 객체와 유사함.)
								if (callback){
									callback(result);
								} // 콜백 if 종료
							}, // success 종료
							error : function(xhr, status, er){
								if (error){
									error(er);
								} // 에러 if 종료
							} // error 종료
						}); // ajax 종료
					} // function update 종료
					
					function get(rno, callback, error){
						
						$.get("/replies/" + rno + ".json", function(result){
							
							if(callback){
								callback(result);
							} // 콜백 if 종료
						}).fail(function(xhr, status, err){
							if(error){
								error();
							} // 에러 if 종료
						}); // fail 종료
					} // function get 종료
					
					function displayTime(timeValue){
						
						var today = new Date();
						
						var gap = today.getTime() - timeValue;
						
						var dateObj = new Date(timeValue);
						var str = "";
						
						if(gap < (1000 * 60 * 60 * 24)){
							
							var hh = dateObj.getHours();
							var mi = dateObj.getMinutes();
							var ss = dateObj.getSeconds();
							
							return [ (hh > 9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi,
								':', (ss > 9 ? '' : '0') + ss ].join('');
						}else{
							
							var yy = dateObj.getFullYear();
							var mm = dateObj.getMonth() + 1; // getMonth는 0이 기본값이라 +1 해야함
							var dd = dateObj.getDate();
							
							return [ yy, '/', (mm > 9 ? '' : '0') + mm, '/',
								(dd > 9 ? '' : '0') + dd ].join('');
						} // if문 종료
					} // function displayTime 종료
					;
					
					return { 
						add : add,
						getList : getList,
						remove : remove,
						update : update,
						get : get,
						displayTime : displayTime
						};
					})(); // 변수 생성
 
 
 