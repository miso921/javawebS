package com.spring.javawebS.dao;

import org.apache.ibatis.annotations.Param;

import com.spring.javawebS.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemberIdCheck(@Param("mid") String mid);

	public MemberVO getMemberNickCheck(@Param("nickName") String nickName);

	public int setMemberJoinOk(@Param("vo") MemberVO vo);

	public void setMemberVisitProcess(@Param("vo") MemberVO vo);

}
