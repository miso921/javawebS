package com.spring.javawebS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.GuestVO;

public interface GuestDAO {

	public List<GuestVO> getGuestList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public int setGuestInput(@Param("vo") GuestVO vo);

	public int totRecCnt();


}
