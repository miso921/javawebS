package com.spring.javawebS;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javawebS.service.GuestService;
import com.spring.javawebS.vo.GuestVO;

@Controller
@RequestMapping("/guest")
public class GuestController {
	
	@Autowired
	GuestService guestService;
	
	@RequestMapping(value = "/guestList", method = RequestMethod.GET)
	public String guestListGet(
			@RequestParam(name="pag", defaultValue="1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue="3", required = false) int pageSize,
			Model model) {
		int totRecCnt = guestService.totRecCnt(); // 게시글 전체 가져오기
		int totPage = (totRecCnt % pageSize)== 0 ? totRecCnt / pageSize : (totRecCnt / pageSize) + 1;   // 나머지가 떨어지지 않으면 +1을 하여 전체 페이지 수를 맞춘다.
		int startIndexNo = (pag - 1) * pageSize; // 게시글 시작하는 번호
		int curScrStartNo = totRecCnt - startIndexNo; // 화면에 보여지는 게시글 시작 번호
		
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize; // 내가 위치한 블럭 번호, 1~3까지는 0블럭, 4~6은 1블럭...
		int lastBlock =  (totPage - 1) / blockSize; // 마지막 블럭 번호
		
		List<GuestVO> vos = guestService.getGuestList(startIndexNo, pageSize);
		
		model.addAttribute("vos",vos);
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("totRecCnt",totRecCnt);
		model.addAttribute("totPage",totPage);
		model.addAttribute("curScrStartNo",curScrStartNo);
		model.addAttribute("blockSize",blockSize);
		model.addAttribute("curBlock",curBlock);
		model.addAttribute("lastBlock",lastBlock);
		
		return "guest/guestList";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.GET)
	public String gusetInputGet() {
		return "guest/guestInput";
	}
	
	@RequestMapping(value = "/guestInput", method = RequestMethod.POST)
	public String gusetInputPost(GuestVO vo, Model model) {
		int res = guestService.setGuestInput(vo);
		
		if(res == 1) {
			return "redirect:/message/guestInputOk";
		}
		else {
			return "redirect:/message/guestInputNo";
		}
	}
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.GET)
	public String adminLoginGet() {
		return "guest/adminLogin";
	}
	
	@RequestMapping(value = "/adminLogout", method = RequestMethod.GET)
	public String adminLogoutGet(HttpSession session) {
		session.invalidate(); // 세션만 끊으면 되기 때문에 request를 사용하지 않아도 되기 때문에 HttpServletRequest을 생성하지 않아도 된다!
		return "redirect:/message/adminLogout";
	}
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public String adminLoginPost(HttpServletRequest request,
			@RequestParam(name="mid", defaultValue="", required=false) String mid,
			@RequestParam(name="pwd", defaultValue="", required=false) String pwd) {
		int res = guestService.getAdminCheck(mid, pwd);
		
		if(res == 1) {
			HttpSession session = request.getSession();
			session.setAttribute("sAdmin", "adminOk");
			return "redirect:/message/guestAdminOk";
		}
		else return "redirect:/message/guestAdminNo";
	}
	
}
