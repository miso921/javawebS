package com.spring.javawebS.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.GuestDAO;
import com.spring.javawebS.vo.GuestVO;

@Service
public class GuestServiceImpl implements GuestService {
	
	@Autowired
	GuestDAO guestDAO;

	@Override
	public List<GuestVO> getGuestList(int startIndexNo, int pageSize) {
		return guestDAO.getGuestList(startIndexNo, pageSize);
	} 

	@Override
	public int setGuestInput(GuestVO vo) {
		return guestDAO.setGuestInput(vo);
	}

	@Override
	public int getAdminCheck(String mid, String pwd) {
		int res = 0;
		if(mid.equals("adminOk") && pwd.equals("1234")) res = 1;
			// 오버라이드 되어있기 때문에 선언부 수정이 불가하여 http객체를 사용할 수 없기 때문에 컨트롤러에서 수행하기!
		return res;
	}

	@Override
	public int totRecCnt() {
		return guestDAO.totRecCnt();
	}

	@Override
	public int setGuestDelete(int idx) {
		
		return guestDAO.setGuestDelete(idx);
	}



}
