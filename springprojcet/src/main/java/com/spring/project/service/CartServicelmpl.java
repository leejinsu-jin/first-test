package com.spring.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.dao.BookDao;
import com.spring.project.dao.CartDao;
import com.spring.project.model.AttachImageVO;
import com.spring.project.model.CartDTO;
@Service
public class CartServicelmpl implements CartService {
	
	@Autowired
	CartDao dao;
	@Autowired
	CartService cartservice;
	@Autowired
	BookDao bookdao;
	@Override
	public int addCart(CartDTO cart) throws Exception {
			return dao.addCart(cart);
	}

	@Override
	public int deleteCart(int cartId) {
		// TODO Auto-generated method stub
		return dao.deleteCart(cartId);
	}

	@Override
	public int modifyCount(CartDTO cart) {
		// TODO Auto-generated method stub
		return dao.modifyCount(cart);
	}

	@Override
	public List<CartDTO> getCart(String memberId) {
		// TODO Auto-generated method stub
		return dao.getCart(memberId);
	}

	@Override
	public CartDTO checkCart(CartDTO cart) {
		// TODO Auto-generated method stub
		return dao.checkCart(cart);
	}
	

	@Override
	public List<CartDTO> getCartList(String memberId) {
		// TODO Auto-generated method stub
		List<CartDTO> cart = getCart(memberId);
		for(CartDTO dto : cart) {
			//종합 정보 초기화
			dto.initSaleTotal();
			
			/* 이미지 정보 얻기 */
			int bookId = dto.getBookId();
			List<AttachImageVO> imageList = bookdao.getAttachList(bookId);
			dto.setImageList(imageList);
		}
		return cart;
	}

	@Override
	public int deleteOrderCart(CartDTO dto) {
		// TODO Auto-generated method stub
		return dao.deleteOrderCart(dto);
	}

	

}
