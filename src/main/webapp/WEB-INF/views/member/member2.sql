show tables;

create table member2 (
  idx      int not null auto_increment, 	/* 회원 고유번호 */
  mid      varchar(20) not null,					/* 회원 아이디(중복불허) */
  pwd      varchar(100) not null,					/* 회원 비밀번호(SHA256 암호화 처리) */
  nickName varchar(20) not null,					/* 회원 별명(중복불허/수정가능) */
  name     varchar(20) not null,					/* 회원 성명 */
  gender   varchar(4) default '남자',    	/* 회원 성별 */
  birthday datetime default now(),				/* 회원 생일 */
  tel			 varchar(15),										/* 전화번호(010-1234-5678) */
  address  varchar(100),									/* 주소(다음 우편번호API 활용) */
  email    varchar(50) not null,					/* 이메일(아이디/비밀번호 분실시에 사용)-형식체크필수 */
  homePage varchar(50),										/* 홈페이지(블로그)주소 */
  job			 varchar(20),										/* 회원 직업 */
  hobby    varchar(100),									/* 취미(2개이상 선택가능, 구분자는 '/'로 한다.) */
  photo		 varchar(100) default 'noimage.jpg',	/* 회원 사진 */
  content	 text,													/* 회원 소개 */
  userInfor char(6) default '공개',				/* 회원정보 공개여부(공개/비공개) */
  userDel   char(2) default 'NO',					/* 회원 탈퇴신청여부(NO:현재 활동중, OK:탈퇴신청중) */
  point			int default 100,							/* 회원 누적포인트(가입포인트100증정, 1회방문시 10포인트증가, 1일 최대 50점까지 증가 */
  level     int default 1, 							/* 회원등급(0:관리자, 1:우수회원, 2:정회원, 3:준회원) */
  visitCnt  int default 0,							/* 총 방문횟수 */
  startDate datetime default now(),			/* 최초 가입일 */
  lastDate  datetime default now(),			/* 마지막 접속일 */
  todayCnt  int default 0,							/* 오늘 방문한 횟수 */
  primary key (idx),									  /* 주키 : idx(고유번호) primary key를 여러개 하면 외래키 설정 시 복잡해지므로 한개만 정한다! */
  unique key (mid)                      /* 유니크키(중복방지키) : mid */
);

desc member2;

drop table member2;

select * from member2;

/* 회원 실시간 대화를 위한 테이블 */
create table memberChat (
  idx   int  not null auto_increment primary key,
  nickName varchar(20) not null,
  chat  varchar(100) not null
);
desc memberChat;

select * from memberChat order by idx desc limit 50;

select chat.* from (select * from memberChat order by idx desc limit 50) chat order by idx asc;