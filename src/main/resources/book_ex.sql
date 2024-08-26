create sequence seq_board; -- 자동번호 객체 생성

create table tbl_board (
	bno number(10,0),
	title varchar2(200) not null,
	content varchar2(2000) not null,
	writer varchar2(50) not null,
	regdate date default sysdate,
	updatedate date default sysdate
); -- tbl_board 테이블 생성(번호, 제목, 내용, 작성자, 작성일, 수정일)

alter table tbl_board add constraint pk_board primary key (bno);

select * from tbl_board;

insert into tbl_board (bno, title, content, writer) 
values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00');

select * from TBL_BOARD order by bno desc; -- bno 역순으로 출력

select /*+ INDEX_DESC(tbl_board pk_board) */ * from tbl_board where bno > 0; -- 힌트 제공 /*+ INDEX_DESC(tbl_board pk_board) */

select /*+ FULL(tbl_board) */ * from tbl_board order by bno desc;

select /*+ INDEX_DESC(tbl_board pk_board) */ rownum rn, bno, title, content from tbl_board where bno > 0; -- bno역순으로 rownum 부여하여 출력

select /*+ INDEX_DESC(tbl_board pk_board) */ rownum rn, bno, title, content from tbl_board where rownum <= 10; -- rownum을 이용하여 bno 역순으로 10개씩 출력

insert into tbl_board(bno, title, content, writer)
(select seq_board.nextval, title, content, writer from tbl_board); -- 재귀 복사 더미데이터 (여러번 실행하면 2배씩 복사됌)


