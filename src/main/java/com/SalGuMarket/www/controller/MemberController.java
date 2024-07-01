package com.SalGuMarket.www.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SalGuMarket.www.domain.BoardVO;
import com.SalGuMarket.www.domain.FileVO;
import com.SalGuMarket.www.domain.HeartVO;
import com.SalGuMarket.www.domain.PagingVO;
import com.SalGuMarket.www.handler.FileHandler;
import com.SalGuMarket.www.handler.PagingHandler;
import com.SalGuMarket.www.security.MemberVO;
import com.SalGuMarket.www.service.BoardService;
import com.SalGuMarket.www.service.MailService;
import com.SalGuMarket.www.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final MailService mailService;
	private final BoardService boardService;

	private final PasswordEncoder passwordEncoder;
	private final FileHandler fileHandler;

	@GetMapping("/login")
	public void login() {
	}

	@GetMapping("/register")
	public void register() {
	}

	@PostMapping("/register")
	public String register(MemberVO mvo, RedirectAttributes re) {
		log.info(">>>> mvo >>> " + mvo);
		mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
		int isOK = memberService.insert(mvo);
		if (isOK > 0)
			re.addFlashAttribute("reg", "1");
		return "redirect:/";
	}

	@GetMapping("/mypage")
	public String mypage(HttpServletRequest request, Principal p, @RequestParam(name="email", required = false) String email, Model m) {
		log.info(">>>>>>>>>>>>>>>>>>>email"+email);
		if(email != null) {
			MemberVO mvo = memberService.selectEmail(email);
			m.addAttribute("mvo", mvo);
			FileVO fvo = memberService.getFile(email);
			if(fvo!=null) {
			String fileName = fvo.getFileName();
			m.addAttribute("fileName", fileName);
			}
			return "redirect:/member/otherspage?email="+email;
		}else {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("mvo", mvo);
			FileVO fvo = memberService.getFile(mvo.getEmail());
			if(fvo!=null) {
				String fileName = fvo.getFileName();				
				m.addAttribute("fileName", fileName);
				m.addAttribute("fvo", fvo);
			}
			log.info(">>>>>>>>>>>>.."+mvo);
			return "/member/mypage";
		}
	}

	@PostMapping("/profile")
	public String profile(@RequestParam(name = "email") String email,
			@RequestParam(name = "file", required = false) MultipartFile file, Model m) {
		FileVO fvo = null;
		MemberVO mvo = memberService.selectEmail(email);
		String nick = mvo.getNickName();
		if (file != null) {
			fvo = fileHandler.uploadProfile(nick, file);
		} else {
			// 미선택시 기본 프사로
		}
		mvo.setFvo(fvo);
		mvo.setIsProfile(memberService.setProfile(mvo));
		m.addAttribute("mvo", mvo);
		return "redirect:/member/mypage";
	}
	
	@PostMapping("/heart")
	public String heart(HeartVO hvo, Principal p, Model m) {
		memberService.insertHeart(hvo);
		
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
			//List<HeartVO> isdel = memberService.isdel(hvo.getBno(), p.getName());
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
		return "redirect:/board/boardDetail?bno="+hvo.getBno();
	}
	
	@PostMapping("/delHeart")
	public String delHeart(HeartVO hvo, Principal p, Model m) {
		memberService.delHeart(hvo);
		
		if(p != null) {
			MemberVO mvo = memberService.selectEmail(p.getName());
			m.addAttribute("loginmvo", mvo);
			//List<HeartVO> isdel = memberService.isdel(hvo.getBno(), p.getName());
		}else {
			MemberVO mvo = new MemberVO();
			m.addAttribute("loginmvo", mvo);
		}
		return "redirect:/board/boardDetail?bno="+hvo.getBno();
	}

	@GetMapping("/otherspage")
	public void otherspage(@RequestParam(name="email", required = false) String email, Model m) {
		log.info(">>>>>>>>>>>>>>>>>>>"+email);
		MemberVO mvo = memberService.selectEmail(email);
		FileVO fvo = memberService.getFile(email);
		m.addAttribute("fvo", fvo);
		m.addAttribute("mvo", mvo);
	}
	
	@GetMapping("/{email}/{page}")
	@ResponseBody
	public PagingHandler list(@PathVariable("email")String email, @PathVariable("page")int page) {
		log.info(">>>> email >> " +email+"/ page >>" + page);
		//비동기 => 한 객제만 전송 가능
		PagingVO pgvo = new PagingVO(page, 5);
		PagingHandler ph = memberService.getBoardList(email, pgvo);
		return ph;
	}
	
	@GetMapping("/heart/{email}/{page}")
	@ResponseBody
	public PagingHandler HeartList(@PathVariable("email")String email, @PathVariable("page")int page) {
		log.info(">>>> email >> " +email+"/ page >>" + page);
		//비동기 => 한 객제만 전송 가능
		List<HeartVO> list = memberService.getHeart(email);
		List<BoardVO> blist = new ArrayList<>();
		PagingVO pgvo = new PagingVO(page, 5);
		for(HeartVO hvo : list) {
			blist.add(memberService.getHeartBoard(hvo.getBno(), pgvo));
		}
		int totalCount = memberService.selectBnoTotalCount(email);
		PagingHandler ph = new PagingHandler(pgvo, totalCount, blist, 1);
		return ph;
	}
	
	@GetMapping("/list")
	public void list (Model m, PagingVO pgvo) {
	    // 새로운 PagingVO 객체를 생성하고 qty를 9로 설정
		log.info("pgvo::::::::::::::::::"+pgvo);
	    int totalCount = memberService.getTotalCount(pgvo);
	    PagingHandler ph = new PagingHandler(pgvo, totalCount,9);

	    List<MemberVO> list = memberService.getList(pgvo);
	    
	    for(MemberVO mvo : list) {
	    	if(mvo.getIsProfile() == 1) {
	    		mvo.setFvo(memberService.getFile(mvo.getEmail()));
	    	}
	    }
	    
		m.addAttribute("list", list);
		m.addAttribute("pgvo", pgvo);
		m.addAttribute("ph", ph);
	}

//	관리자가 회원 탈퇴
	@GetMapping("/remove")
	public String remove(@RequestParam("email") String email, RedirectAttributes re) {
		log.info("email:::::::::::::::::::" + email);
		memberService.remove(email);
		re.addFlashAttribute("remove", "1");
		return "redirect:/member/list?pageNo=1&qty=9";
	}

//  회원 직접 탈퇴
	@GetMapping("/delete")
	public String delete(Principal p, RedirectAttributes re) {
		String email = p.getName();
		memberService.delete(email);
		// logout(req,res);
		re.addFlashAttribute("logout", "1");
		return "redirect:/member/login";

	}

	// 아이디 중복확인
	@GetMapping(value = "/checkEmail/{email}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> checkEamil(@PathVariable("email") String email) {
		log.info("email::::::::::::::" + email);
		MemberVO mvo = memberService.selectEmail(email);
		log.info("mvo::::::::::::::" + mvo);
		return mvo == null ? new ResponseEntity<>("1", HttpStatus.OK)
				: new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 닉네임 중복확인
	@GetMapping(value = "/checkNick/{nickName}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> checkNick(@PathVariable("nickName") String nickName) {
		log.info("nick_name::::::::::::::" + nickName);
		MemberVO mvo = memberService.selectNickName(nickName);
		log.info("mvo::::::::::::::" + mvo);
		return mvo == null ? new ResponseEntity<>("1", HttpStatus.OK)
				: new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/findId")
	public void findID() {}
	
	@GetMapping(value = "/findid/{name}/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> userdata(@PathVariable("name") String name, @PathVariable("email") String email) {
        MemberVO mvo = new MemberVO();
        mvo.setName(name);
        mvo.setRegEmail(email);
        log.info("mvo:::" + mvo);
        
        Map<String, String> resultMap = mailService.check(mvo);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
	
	@GetMapping("/findPw")
	public void findPw() {}
	
	@PostMapping("/findpwd")
	public String findpw(MemberVO mvo, HttpSession ses, RedirectAttributes re) {
	    log.info("mvo:::" + mvo);
	    int isok = memberService.checkpw(mvo);

	    if (isok > 0) {
	        // 세션에 이메일 저장
	        ses.setAttribute("email", mvo.getEmail());
	        return "member/updatePwd";
	    } else {
	        re.addFlashAttribute("no", "1");
	        return "redirect:/member/findPw";
	    }
	}
	
	@PostMapping("/updatePwd")
	public String updatePwd(MemberVO mvo, RedirectAttributes re, HttpSession ses) {
		String email = (String) ses.getAttribute("email");
		mvo.setEmail(email);
		mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
		memberService.updatePWD(mvo);
		re.addFlashAttribute("updatepwd", "1");
		return "redirect:/member/login";
	}
}
