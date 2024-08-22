# SpringStudy
24.08 스프링 공부 시작

*** 프론트 페이지 이동시 <a href>코드보다 자바스크립트를 이용하는게 좋음.
<script type="text/javascript"> /* 자바스크립트 코드임을 명시 */
	$(document).ready(function(){ /* 문서의 준비 단계에서의 함수(기능) */
		$("#regBtn").on("click", function(){ /* 21행의 id="regBtn" 클릭 동작(기능) */
			self.location = "/board/register"; /* 현재문서를 /board/register로 이동 */
		});
	});
</script>

*** <js> 도배 버그 방지 코드
history.replaceState({}, null, null);

*** <js> form태그 버튼 기본사용 안함
e.preventDefault(); // button 기본 사용을 안함. submit 안됨
