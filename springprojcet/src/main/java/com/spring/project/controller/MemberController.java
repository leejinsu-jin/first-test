package com.spring.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.project.model.MemberVO;
import com.spring.project.service.MemberSerivce;




@Controller
//@RequestMapping(value="/member")
public class MemberController {
	@Autowired
	MemberSerivce memberservice;
	public MemberController() {

		System.out.println("@MemberController 스프링 자동생성");
	}
	
	@GetMapping("/member/join")
	public String joinget() {
		return "/member/join";
	}
	//회원가입
	@PostMapping("/member/join")
	public ModelAndView memberjoinPost(MemberVO memberVo) {
		// 값 받기 : @RequestParam 스프링이 자동으로 넣어줌
		
		System.out.println("member://"+memberVo);
		// service
		int rs = memberservice.insert(memberVo);
	
		ModelAndView mav = new ModelAndView();
		if(rs == 1) {
			mav.setViewName("redirect:/main");			
		}else {
			mav.setViewName("redirect:/member/join");
		}		

		return mav;
	}
	//아이디 중복 체크 
	@PostMapping("/member/memberIdChk")
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception {
		System.out.println("memberId://"+memberId);
		int result = memberservice.idChk(memberId);
		if(result !=0) {
			return "fail"; //중복 아디 존재 체크좀해라 시이이이이발 제발 
		}else {
			return "success"; // 중복아디 없어 시발
			}
		}

		
	
	//로그인 페이지 이동
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("/member/login");


	}
	  /* 로그인 */
    @RequestMapping(value="/member/login", method=RequestMethod.POST)
    public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception{
        
        System.out.println("login 메서드 진입");
        System.out.println("전달된 데이터 : " + member);
        HttpSession session = request.getSession();
        MemberVO lvo = memberservice.memberLogin(member);

        if(lvo == null) {                                // 일치하지 않는 아이디, 비밀번호 입력 경우
            
            int result = 0;
            rttr.addFlashAttribute("result", result);
            System.out.println(result);
            return "redirect:/member/login";
            
        }
        
        session.setAttribute("member", lvo);             // 일치하는 아이디, 비밀번호 경우 (로그인 성공)
       // System.out.println(lvo);
        return "redirect:/main";
 
    }
 

//	@RequestMapping(value = "/main", method = RequestMethod.GET)
//	public ModelAndView main() {
//		return new ModelAndView("main");
//	}

    /* 메인페이지 로그아웃 */
    @RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
    public String logoutMainGET(HttpServletRequest request) throws Exception{
        
        System.out.println("logoutMainGET메서드 진입");
        
        HttpSession session = request.getSession();
        
        session.invalidate();
        
        return "redirect:/main";        
        
    }

}

