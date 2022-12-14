package com.spring.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.project.dao.MemberDao;
import com.spring.project.model.MemberVO;
import com.spring.project.model.OrderDTO;
import com.spring.project.model.OrderPageDTO;
import com.spring.project.service.MemberSerivce;
import com.spring.project.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	MemberDao dao;
	@Autowired
	OrderService orderservice;
	@Autowired
	MemberSerivce memberservice;
	
	@GetMapping("/order/{memberId}")
	public String orderPgaeGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
		
		//System.out.println("memberId : " + memberId);
		//System.out.println("orders : " + opd.getOrders());
		model.addAttribute("orderList", orderservice.getGoodsInfos(opd.getOrders()));
		model.addAttribute("memberInfo", dao.getMemberInfo(memberId));
		return "/order";
		
	}
	
	@PostMapping("/order")
	public String orderPagePost(OrderDTO od,  HttpServletRequest request) {
		
		System.out.println("정보들아찍혀라"+od);	
		orderservice.order(od);
		
		MemberVO member = new MemberVO();
		member.setMemberId(od.getMemberId());
		
		System.out.println("회원정보좀 찍혀주세요 제발"+member);
		
		HttpSession session = request.getSession();
		
		try {
			MemberVO memberLogin = (member);
			memberLogin.setMemberPw("");
			
			session.setAttribute("member", memberLogin);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "redirect:/main";
	}
 
}
