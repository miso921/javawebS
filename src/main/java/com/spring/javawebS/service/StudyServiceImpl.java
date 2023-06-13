package com.spring.javawebS.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javawebS.dao.StudyDAO;
import com.spring.javawebS.vo.MemberVO;

@Service
public class StudyServiceImpl implements StudyService {

	@Autowired
	StudyDAO studyDAO;

	@Override
	public ArrayList<MemberVO> getMemberList() {
		return studyDAO.getMemberList();
	} 
}
