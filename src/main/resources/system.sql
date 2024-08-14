create user book_ex identified by book_ex default tablespace users temporary tablespace temp; -- 유저 생성

grant connect, dba to book_ex; -- 권한 부여