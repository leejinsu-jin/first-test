package com.spring.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.dao.BookDao;
import com.spring.project.dao.CartDao;
import com.spring.project.dao.MemberDao;
import com.spring.project.dao.OrderDao;
import com.spring.project.model.AttachImageVO;
import com.spring.project.model.BookVO;
import com.spring.project.model.CartDTO;
import com.spring.project.model.MemberVO;
import com.spring.project.model.OrderCancelDTO;
import com.spring.project.model.OrderDTO;
import com.spring.project.model.OrderItemDTO;
import com.spring.project.model.OrderPageItemDTO;

@Service
public class OrderServicelmpl implements OrderService {
	
	@Autowired
	OrderDao dao;
	@Autowired
	OrderService orderservice;
	@Autowired
	CartDao cartdao;
	@Autowired
	BookDao bookdao;
	@Autowired
	MemberDao memberdao;
	@Autowired
	MemberSerivce memberservice;
	@Autowired
	bookService bookservice;
	
	@Autowired
	AdminService adminservice;
	@Override
	public OrderPageItemDTO getGoodsInfos(int bookId) {
		// TODO Auto-generated method stub
		return dao.getGoodsInfos(bookId);
	}

	@Override
	public List<OrderPageItemDTO> getGoodsInfos(List<OrderPageItemDTO> orders) {
		// TODO Auto-generated method stub
	List<OrderPageItemDTO> result = new ArrayList<OrderPageItemDTO>();		
		
		for(OrderPageItemDTO ord : orders) {
			
			OrderPageItemDTO goodsInfo = getGoodsInfos(ord.getBookId());			

			goodsInfo.setBookCount(ord.getBookCount());	
			
			goodsInfo.initSaleTotal();			
			List<AttachImageVO> imageList = adminservice.getAttachInfo(goodsInfo.getBookId());
			
			goodsInfo.setImageList(imageList);
			result.add(goodsInfo);			
		}		
		
		return result;
	}

	@Override
	public OrderItemDTO getOrderInfos(int bookId) {
		// TODO Auto-generated method stub
		return dao.getOrderInfos(bookId);
	}

	@Override
	public int enrollOrder(OrderDTO ord) {
		// TODO Auto-generated method stub
		return dao.enrollOrder(ord);
	}

	@Override
	public int enrollOrderItem(OrderItemDTO orid) {
		// TODO Auto-generated method stub
		return dao.enrollOrderItem(orid);
	}

	@Override
	public int deductMoney(MemberVO member) {
		// TODO Auto-generated method stub
		return dao.deductMoney(member);
	}

	@Override
	public int deductStock(BookVO book) {
		// TODO Auto-generated method stub
		return dao.deductStock(book);
	}

	@Override
	@Transactional
	public void order(OrderDTO ord) {
		// TODO Auto-generated method stub
		/* 사용할 데이터가져오기 */
		/* 회원 정보 */
		MemberVO member = memberdao.getMemberInfo(ord.getMemberId());
		System.out.println("회원정보야 찍혀바라"+member);
		/* 주문 정보 */
		List<OrderItemDTO> ords = new ArrayList<>();
		for(OrderItemDTO oit : ord.getOrders()) {
			OrderItemDTO orderItem = dao.getOrderInfos(oit.getBookId());
			// 수량 셋팅
			orderItem.setBookCount(oit.getBookCount());
			// 기본정보 셋팅
			orderItem.initSaleTotal();
			//List객체 추가
			ords.add(orderItem);
		}
		/* OrderDTO 셋팅 */
		ord.setOrders(ords);
		ord.getOrderPriceInfo();
		
		/*DB 주문,주문상품(,배송정보) 넣기*/
		
		/* orderId만들기 및 OrderDTO객체 orderId에 저장 */
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
		String orderId = member.getMemberId() + format.format(date);
		ord.setOrderId(orderId);

		/* db넣기 */
		dao.enrollOrder(ord);		//vam_order 등록
		for(OrderItemDTO oit : ord.getOrders()) {		//vam_orderItem 등록
			oit.setOrderId(orderId);
			dao.enrollOrderItem(oit);
	}
		/* 비용 포인트 변동 적용 */
		
		/* 비용 차감 & 변동 돈(money) Member객체 적용 */
		int calMoney = member.getMoney();
		calMoney -= ord.getOrderFinalSalePrice();
		member.setMoney(calMoney);
		
		/* 포인트 차감, 포인트 증가 & 변동 포인트(point) Member객체 적용 */
		int calPoint = member.getPoint();
		calPoint = calPoint - ord.getUsePoint() + ord.getOrderSavePoint();	// 기존 포인트 - 사용 포인트 + 획득 포인트
		member.setPoint(calPoint);
		
		/* 변동 돈, 포인트 DB 적용 */
		dao.deductMoney(member);
		
		/* 재고 변동 적용 */
		for(OrderItemDTO oit : ord.getOrders()) {
			/* 변동 재고 값 구하기 */
			BookVO book = bookservice.getGoodsInfo(oit.getBookId());
			book.setBookStock(book.getBookStock() - oit.getBookCount());
			/* 변동 값 DB 적용 */
			dao.deductStock(book);

					}
		/* 장바구니 제거 */
		for(OrderItemDTO oit : ord.getOrders()) {
			CartDTO dto = new CartDTO();
			dto.setMemberId(ord.getMemberId());
			dto.setBookId(oit.getBookId());
			
			cartdao.deleteOrderCart(dto);
		}
			}

	@Override
	public int orderCancle(String orderId) {
		// TODO Auto-generated method stub
		return dao.orderCancle(orderId);
	}

	@Override
	public List<OrderItemDTO> getOrderItemInfo(String orderId) {
		// TODO Auto-generated method stub
		return dao.getOrderItemInfo(orderId);
	}

	@Override
	public OrderDTO getOrder(String orderId) {
		// TODO Auto-generated method stub
		return dao.getOrder(orderId);
	}
	
	/*주문 취소 */
	@Override
	@Transactional
	public void orderCancle(OrderCancelDTO dto) {
		/* 주문, 주문상품 객체 */
		/*회원*/
			MemberVO member = memberservice.getMemberInfo(dto.getMemberId());
		/*주문상품*/
			List<OrderItemDTO> ords = dao.getOrderItemInfo(dto.getOrderId());
			for(OrderItemDTO ord : ords) {
				ord.initSaleTotal();
			}
		/* 주문 */
			OrderDTO orw = dao.getOrder(dto.getOrderId());
			orw.setOrders(ords);
			
			orw.getOrderPriceInfo();
		
			
	/* 주문상품 취소 DB */
			dao.orderCancle(dto.getOrderId());
			
			/* 돈, 포인트, 재고 변환 */
			/* 돈 */
			int calMoney = member.getMoney();
			calMoney += orw.getOrderFinalSalePrice();
			member.setMoney(calMoney);
			
			/* 포인트 */
			int calPoint = member.getPoint();
			calPoint = calPoint + orw.getUsePoint() - orw.getOrderSavePoint();
			member.setPoint(calPoint);
		

			/* DB적용 */
			dao.deductMoney(member);
			
			/* 재고 */
			for(OrderItemDTO ord : orw.getOrders()) {
				BookVO book = bookservice.getGoodsInfo(ord.getBookId());
				book.setBookStock(book.getBookStock() + ord.getBookCount());
				dao.deductStock(book);
			}
				}
	}
