package com.spring.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.project.dao.CartDao;
import com.spring.project.model.CartDTO;
import com.spring.project.model.MemberVO;
import com.spring.project.service.CartService;

@Controller
public class CartController {
 @Autowired
 CartService cartservice;
 @Autowired
 CartDao dao;
 //@Autowired
// CartDao dao;
 
 @PostMapping("/cart/add")
 @ResponseBody
	public String addCartPOST(CartDTO cart, HttpServletRequest request) throws Exception {
	// 로그인 체크
			HttpSession session = request.getSession();
			MemberVO mvo = (MemberVO)session.getAttribute("member");
			if(mvo == null) {
				return "5";
			}
		      /// 카트 등록
		      CartDTO checkCart = cartservice.checkCart(cart);
		      if(checkCart != null) {
		         return "2";
		      }

		      int result = dao.addCart(cart);
		      return result + "";
		   

			
		}
 @GetMapping("/cart/{memberId}")
	public String cartPageGET(@PathVariable("memberId") String memberId, Model model) {
	
	 model.addAttribute("cartInfo", cartservice.getCartList(memberId));

		return "/cart";
	}
 /* 장바구니 수량 수정 */
	@PostMapping("/cart/update")
	public String updateCartPOST(CartDTO cart) {
		dao.modifyCount(cart);
		return "redirect:/cart/" + cart.getMemberId();

	}
	/*장바구니 수량 삭제 */
	@PostMapping("/cart/delete")
	public String deleteCartPOST(CartDTO cart) {
		dao.deleteCart(cart.getCartId());
		return "redirect:/cart/" +cart.getMemberId();
	}
}
