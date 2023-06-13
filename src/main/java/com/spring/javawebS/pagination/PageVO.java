package com.spring.javawebS.pagination;

import lombok.Data;

@Data
public class PageVO {
	private int pag;
	private int pageSize;
	private int totRecCnt;
	private int totPage; 
	private int startIndexNo;  // 해당 페이지에 첫번째 인덱스 값
	private int curScrStartNo; // 화면에 보여지는 시작 번호
	private int blockSize;   // 블록 개수
	private int curBlock;    // 현재 블록
	private int lastBlock;   // 마지막 블록
	
	private String part; // 확장성을 위한 게시판 별 분류 
}
