package com.spring.javawebS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.GuestVO;

public interface GuestDAO {

//	public List<GuestVO> getGuestList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);
	public ArrayList<GuestVO> getGuestList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public int setGuestInput(@Param("vo") GuestVO vo);

	public int totRecCnt();

	public int setGuestDelete(@Param("idx") int idx);

}
