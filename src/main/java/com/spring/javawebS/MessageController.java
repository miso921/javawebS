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
		return "include/message";
	}
}
