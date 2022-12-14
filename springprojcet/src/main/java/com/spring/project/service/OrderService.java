package com.spring.project.service;

import java.util.List;

import com.spring.project.model.BookVO;
import com.spring.project.model.MemberVO;
import com.spring.project.model.OrderCancelDTO;
import com.spring.project.model.OrderDTO;
import com.spring.project.model.OrderItemDTO;
import com.spring.project.model.OrderPageItemDTO;

public interface OrderService {
	/* 주문 상품 정보  */	
	public OrderPageItemDTO getGoodsInfos(int bookId);
	
	/* 주문 정보 */
	public List<OrderPageItemDTO> getGoodsInfos(List<OrderPageItemDTO> orders);
	
	/*주문 상품 정보 (주문처리) */
	public OrderItemDTO getOrderInfos(int bookId);
	
	/*주문 테이블 등록 */
	public int enrollOrder(OrderDTO ord);
	
	/* 주문 아이템 테이블 등록 */
	public int enrollOrderItem(OrderItemDTO orid);
	
	/*주문 금액 차감  */
	public int deductMoney(MemberVO member);
	
	/*주문 재고 차감 */
	public int deductStock(BookVO book);
	
	/*주문 처리 */
	public void order(OrderDTO orw);

	/*주문 취소 */
	public int orderCancle(String orderId);
	
	/* 주문 상품 정보 (주문 취소)*/
	public List<OrderItemDTO> getOrderItemInfo(String orderId);
	
	/*주문 정보(주문취소) */
	public OrderDTO getOrder(String orderId);
	
	/* 주문 취소 */
	public void orderCancle(OrderCancelDTO dto);
	

}
