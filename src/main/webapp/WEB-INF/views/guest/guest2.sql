/* guest2.sql */
show tables;

create table guest2 (
  idx  int not null auto_increment primary key,	/* 방명록 고유번호 */
  name varchar(20) not null, 		/* 방명록 작성자 성명 */
  content  text  not null,			/* 방명록 내용 */
  email    varchar(60),					/* 메일주소 */
  homePage varchar(60),					/* 홈페이지 주소(블로그 주소) */
  visitDate datetime default now(),	/* 방문일자 */
  hostIp 	 varchar(30) not null	/* 방문자 접속 IP */
);

desc guest2;

insert into guest2 values (default,'관리자','방명록 서비스를 시작합니다.','cjsk1126@naver.com','cjsk1126.tistory.com',default,'192.168.50.20');

select * from guest2;
