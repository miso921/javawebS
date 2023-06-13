package com.spring.javawebS.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.GuestDAO;

@Service
public class PageProcess {

	@Autowired
	GuestDAO guestDAO;
	
	public PageVO totRecCnt(int pag, int pageSize, String section, String part, String searchString) {
		PageVO pageVo = new PageVO(); // 그냥 vo로 하면 헷갈림
		
		int totRecCnt = 0; // 확장성을 위해 먼저 선언!
		
		if(section.equals("guest")) totRecCnt = guestDAO.totRecCnt();
//		else if(section.equals("member")) totRecCnt = memberDAO.totRecCnt();
//		else if(section.equals("board")) totRecCnt = boardDAO.totRecCnt();
		
		int totPage = (totRecCnt % pageSize)== 0 ? totRecCnt / pageSize : (totRecCnt / pageSize) + 1;   // 나머지가 떨어지지 않으면 +1을 하여 전체 페이지 수를 맞춘다.
		int startIndexNo = (pag - 1) * pageSize; // 게시글 시작하는 번호
		int curScrStartNo = totRecCnt - startIndexNo; // 화면에 보여지는 게시글 시작 번호
		
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize; // 내가 위치한 블럭 번호, 1~3까지는 0블럭, 4~6은 1블럭...
		int lastBlock =  (totPage - 1) / blockSize; // 마지막 블럭 번호
		
		pageVo.setPag(pag);
		pageVo.setPageSize(pageSize);
		pageVo.setTotRecCnt(totRecCnt);
		pageVo.setTotPage(totPage);
		pageVo.setStartIndexNo(startIndexNo);
		pageVo.setCurScrStartNo(curScrStartNo);
		pageVo.setCurBlock(curBlock);
		pageVo.setBlockSize(blockSize);
		pageVo.setLastBlock(lastBlock);
		pageVo.setPart(part);
		
		return pageVo;
	}
	
	
}
