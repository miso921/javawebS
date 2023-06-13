package com.spring.javawebS;

import java.net.CookieStore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawebS.service.MemberService;
import com.spring.javawebS.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	// 로그인 쿠키 생성
	@RequestMapping(value="/memberLogin", method = RequestMethod.GET)
	public String memberLoginGet(HttpServletRequest request) {
		// cookie 처리
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		return "/member/memberLogin";
	}
	
	// 로그인 처리
	@RequestMapping(value="/memberLogin", method = RequestMethod.POST)
	public String memberLoginPost(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name="mid", defaultValue="", required = false) String mid,
			@RequestParam(name="pwd", defaultValue="", required = false) String pwd,
			@RequestParam(name="idSave", defaultValue="", required = false) String idSave,
			HttpSession session) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {// 넘겨주는 값이 논리값이 온다! 둘이 같으면 true, 아니면 false 
			// 회원 인증처리된 경우는? strLevel, session, cookie, 방문포인트, 방문횟수 증가... 쿠키,세션는 request때문에 컨트롤러에서 하는것이 편함, 하지만 전부 컨트롤러에서 하는 것이 좋은 방법은 아님
			String strLevel = "";
			if(vo.getLevel() == 0) strLevel = "관리자";
			else if(vo.getLevel() == 1) strLevel = "우수회원";
			else if(vo.getLevel() == 2) strLevel = "정회원";
			else if(vo.getLevel() == 3) strLevel = "준회원";
			
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			session.setAttribute("sMid", vo.getMid());
			session.setAttribute("sNickName", vo.getNickName());
			
			// 쿠키 저장
			if(idSave.equals("on")) {
				Cookie cookie = new Cookie("cMid", mid);
				cookie.setMaxAge(60 * 60 * 24 * 7);
				response.addCookie(cookie);
			}
			// 쿠키 삭제
			else {
				Cookie[] cookies = request.getCookies();
				for(int i=0; i<cookies.length; i++) {
					if (cookies[i].getName().equals("cMid")) {
						cookies[i].setMaxAge(0);
						response.addCookie(cookies[i]);
						break;
					}
				}
			}
			// 로그인한 사용자의 오늘 방문횟수, 방문포인트 누적한다.
			memberService.setMemberVisitProcess(vo);
			return "redirect:/message/memberLoginOk?mid="+mid;
		}
		else {
			return "redirect:/message/memberLoginNo";
		}
	}
	
	@RequestMapping(value="/memberJoin", method = RequestMethod.GET)
	public String memberJoinGet() {
		return "/member/memberJoin";
	}
	
	@RequestMapping(value="/memberJoin", method = RequestMethod.POST)
	public String memberJoinPost(MemberVO vo) {
		
		// 백에서 한번 더 아이디, 닉네임 중복 체크
		if (memberService.getMemberIdCheck(vo.getMid()) != null) return "redirect:/message/idCheckNo";
		if (memberService.getMemberNickCheck(vo.getNickName()) != null) return "redirect:/message/nickCheckNo";
		
		// 비밀번호 암호화(Spring Security)
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		// 사진파일이 업로드되었으면 사진파일을 서버 파일시스템에 저장해준다!(나중에 Service에서 수행하기) 
		// 체크가 완료되면 vo에 담긴 자료를 DB에 저장해준다!(회원가입)
		int res = memberService.setMemberJoinOk(vo);
		
		if(res == 1) return "redirect:/message/memberJoinOk";
		else return "redirect:/message/memberJoinNo";
	}
	
	// 아이디 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberIdCheck", method= RequestMethod.POST)
	public String memberIdCheckGet(String mid) {
		MemberVO vo = memberService.getMemberIdCheck(mid);
		
		if(vo != null) return "1";
		else return "0";  // aJax방식이므로 1,0만 넘기면 된다!
	}
	
	// 닉네임 중복체크
	@ResponseBody
	@RequestMapping(value = "/memberNickCheck", method= RequestMethod.POST)
	public String memberNickCheckGet(String nickName) {
		MemberVO vo = memberService.getMemberNickCheck(nickName);
		
		if(vo != null) return "1";
		else return "0";  // aJax방식이므로 1,0만 넘기면 된다!
	}
	
	// 회원전용방
	@RequestMapping(value="/memberMain", method=RequestMethod.GET)
	public String memberMainGet() {
		return "/member/memberMain";
	}
	
	
}
