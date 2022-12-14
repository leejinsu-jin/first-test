package com.spring.project.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import com.spring.project.model.BoardVO;
import com.spring.project.service.BoardService;


@Controller
public class BoardController {
	
	@Autowired
	BoardService boardSerivce;
	
 public BoardController() {
	 System.out.println("@Controller 스프링 자동생성");
 }
 

	@GetMapping("/boardwriter")
	public String boardwriter() {
		return "boardwriter";
	}

	@PostMapping("/boardwriter")
	public ModelAndView boardwriterPost(BoardVO BoardVO) {
		// 값 받기 : @RequestParam 스프링이 자동으로 넣어줌
		System.out.println(BoardVO);
		// service
		int rs = boardSerivce.insert(BoardVO);
	//	BoardService.insert(BoardVO);
		ModelAndView mav = new ModelAndView();
		if(rs == 1) {
			mav.setViewName("redirect:/boardwriter");			
		}else {
			mav.setViewName("redirect:/main");
		}		

		return mav;
	}

//	 @RequestMapping(value = "/main", method = RequestMethod.GET)
//		public ModelAndView main() {
//			return new ModelAndView("main");
//	 }
		
		
 
 
	 }

