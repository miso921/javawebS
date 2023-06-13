package com.spring.javawebS;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javawebS.common.ARIAUtil;
import com.spring.javawebS.common.SecurityUtil;
import com.spring.javawebS.service.StudyService;
import com.spring.javawebS.vo.MailVO;
import com.spring.javawebS.vo.MemberVO;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	StudyService studyService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	JavaMailSender mailSender; 
	
	// 암호화연습1 (sha256)
	@RequestMapping(value = "/password/sha256", method=RequestMethod.GET)
	public String sha256Get() {
		return "study/password/sha256";
	}
	// 암호화연습1 (sha256) : 결과처리
	@ResponseBody  // ajax방식일 때 무조건 달아야하는 어노테이션!
	@RequestMapping(value = "/password/sha256", method=RequestMethod.POST, produces="application/text; charset=utf8")
	public String sha256Post(String pwd) {
		SecurityUtil security = new SecurityUtil();
		String encPwd = security.encryptSHA256(pwd);
		pwd = "원본 비밀번호 : " + pwd + "/ 암호화된 비밀번호 : " + encPwd;
		return pwd;
	}
	
	// 암호화연습2 (ARIA)
	@RequestMapping(value = "/password/aria", method=RequestMethod.GET)
	public String ariaGet() {
		return "study/password/aria";
	}
	
	// 암호화연습2 (ARIA) : 결과처리
	@ResponseBody
	@RequestMapping(value = "/password/aria", method=RequestMethod.POST, produces="application/text; charset=utf8")
	public String ariaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		String encPwd = "";
		String decPwd = "";
		
		encPwd = ARIAUtil.ariaEncrypt(pwd); // 예외처리 해준다!
		decPwd = ARIAUtil.ariaDecrypt(encPwd);
		
		pwd = "원본 비밀번호 : " + pwd + "/ 암호화된 비밀번호 : " + encPwd + " / 복호화된 비밀번호 : " + decPwd;
		return pwd;
	}
	
	// 암호화연습2 (bCryptPassword)
	@RequestMapping(value = "/password/bCryptPassword", method=RequestMethod.GET)
	public String bCryptPasswordGet() {
		return "study/password/bCryptPassword";
	}
	
	// 암호화연습2 (bCryptPassword) : 결과처리
	@ResponseBody
	@RequestMapping(value = "/password/bCryptPassword", method=RequestMethod.POST, produces="application/text; charset=utf8")
	public String bCryptPasswordPost(String pwd) {
		String encPwd = "";
		encPwd = passwordEncoder.encode(pwd); // spring security 사용방법 - 1. pom.xml에 spring security를 넣는다! 2. servlet-context에 BCryptPasswordEncoder 객체를 Bean으로 등록 3. BCryptPasswordEncoder를 @Autowired 걸어준다.
		
		pwd = "원본 비밀번호 : " + pwd + "/ 암호화된 비밀번호 : " + encPwd;
		
		return pwd;
	}
	
	// 메일연습 폼
	@RequestMapping(value = "/mail/mailForm", method=RequestMethod.GET)
	public String mailFormGet(Model model) {
		ArrayList<MemberVO> vos = studyService.getMemberList();
		model.addAttribute("vos",vos);
		return "study/mail/mailForm";
	}
	
	// 메일 전송하기
	@RequestMapping(value = "/mail/mailForm", method=RequestMethod.POST)
	public String mailFormPost(MailVO vo, HttpServletRequest request) throws MessagingException { // vo로 받는메일, 제목, 내용 값 모두 받기
		// vo에 받은 것들 모두 꺼내기
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		// 메일 전송을 위한 객체 : MimeMessage(), MimeMessageHelper() / 1. JavaMailSender를 @Autowired 건다.
		MimeMessage message = mailSender.createMimeMessage(); // 2. 메일 만들기
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8"); // 3. 메세지를 보관함에 보내기(코드 편집해야 정상적으로 보낼 수 있음) >> 예외처리(Add throws~~선택)
		
		// 메일 보관함에 회원이 보내온 메세지들의 정보를 모두 저장시킨 후 작업처리하자
		messageHelper.setTo(toMail.split(";")); // 메일주소;메일주소... 이렇게 가져온 toMail을 setTo할 때 ";"을 잘라서 넣는다.(반복문이 아닌데 하나씩 저장되는 이유는?)
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		// 메세지 보관함의 내용(content)에 필요한 정보를 추가로 편집 후 담아서 전송할 수 있도록 한다.
		content = content.replace("\n", "<br />"); // 엔터키를 <br />태그로 변경
		content += "<br><hr><h3>CJ Green에서 보냅니다.</h3><hr><br>";   // html5부터 <br /> '/'를 붙이는데 먼 나라에서는 html4를 사용하는 경우도 많기 때문에 '/' 안붙이는 방향으로!
		content += "<p><img src=\"cid:main.jpg\" width='500px'></p>";  // 이미지 추가 (src는 문자열을 넣어야해서 무조건 큰따옴표를 써야해서 제어코드로 적어준다.)
		content += "<p>방문하기 : <a href='http://49.142.157.251:9090/javaweb10J/Main'>JSP프로젝트</a></p>";
		content += "<hr>";
		messageHelper.setText(content, true);  // 메세지를 보관함에 넣는 명령어!
		
		// 본문에 기재된 그림 파일의 경로를 별도로 표시해준 후 다시 보관함에 담아준다. (cid:파일명)만으로는 어떤 파일인지 알 수 없어 별도 생성
		FileSystemResource file = new FileSystemResource("C:\\javaweb\\springframework\\works\\javawebS\\src\\main\\webapp\\resources\\images\\main.jpg"); // FileSystemResource으로 생성하면 FileSystem 생성하며 경로까지 한번에 적용 가능!  
		messageHelper.addInline("main.jpg", file); // 보관함에 저장(117번 라인)하는 명령!
		
		// 첨부파일 보내기1(서버 파일시스템에 존재하는 파일을 보내기)
		file = new FileSystemResource("C:\\javaweb\\springframework\\works\\javawebS\\src\\main\\webapp\\resources\\images\\chicago.jpg");
		messageHelper.addAttachment("chicago.jpg", file); // 첨부하는 방식의 명령!

		file = new FileSystemResource("C:\\javaweb\\springframework\\works\\javawebS\\src\\main\\webapp\\resources\\images\\main.zip");
		messageHelper.addAttachment("main.zip", file); // zip파일 첨부하는 명령!

//		ServletContext application = request.getSession();
//			request.getRealPath("경로"); 이렇게 생성해서 RealPath 사용한다.> 141번 라인으로 이동
		
		// 파일시스템에 설계한 파일이 저장된 실제경로(realPath)를 이용한 설정
		// realPath는 contextPath(wepapp)까지 찾아줌
//		file = new FileSystemResource(request.getRealPath("/resources/images/paris.jpg")); 구식명령으로 대체명령이 있다는 의미로 취소선이 생김!
		// getRealPath 대신 사용하는 방법
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/paris.jpg"));
		
		messageHelper.addAttachment("paris.jpg", file);
		
		// 메일 전송하기
		mailSender.send(message);
		
		return "redirect:/message/mailSendOk";
	}
	
	
	@RequestMapping(value="/uuid/uuidForm", method=RequestMethod.GET)
	public String uuidFormGet() {
		return "/study/uuid/uuidForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/uuid/uuidForm", method=RequestMethod.POST)
	public String uuidFormPost() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}
	
	
	
}
