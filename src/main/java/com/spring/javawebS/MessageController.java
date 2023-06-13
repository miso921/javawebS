package com.spring.javawebS;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	
	@RequestMapping(value="/message/{msgFlag}", method=RequestMethod.GET)
	public String messageGet(@PathVariable String msgFlag, Model model,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid) {
		
		if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("msg", "방명록이 등록되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("msg", "방명록 등록이 실패하였습니다.");
			model.addAttribute("url", "guest/guestInput");
		}
		else if(msgFlag.equals("guestAdminOk")) {
			model.addAttribute("msg", "관리자 인증이 성공하였습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestAdminNo")) {
			model.addAttribute("msg", "관리자 인증에 실패하였습니다.");
			model.addAttribute("url", "guest/adminLogin");
		}
		else if(msgFlag.equals("adminLogout")) {
			model.addAttribute("msg", "관리자 로그아웃되었습니다!");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("msg", "선택한 방명록이 삭제되었습니다!");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteNo")) {
			model.addAttribute("msg", "선택한 방명록 삭제가 실패했습니다!");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일 발송이 성공했습니다!");
			model.addAttribute("url", "study/mail/mailForm");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("msg", "아이디가 중복되었습니다!");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("msg", "닉네임이 중복되었습니다!");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("msg", "회원가입이 완료되었습니다!");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("msg", "회원가입에 실패했습니다!");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("msg", mid + "님 로그인 되었습니다!");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("msg", mid + "로그인 실패!!");
			model.addAttribute("url", "member/memberLogin");
		}
		return "include/message";
	}
}
