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
		/* ????????? ????????????????????? */
		/* ?????? ?????? */
		MemberVO member = memberdao.getMemberInfo(ord.getMemberId());
		System.out.println("??????????????? ????????????"+member);
		/* ?????? ?????? */
		List<OrderItemDTO> ords = new ArrayList<>();
		for(OrderItemDTO oit : ord.getOrders()) {
			OrderItemDTO orderItem = dao.getOrderInfos(oit.getBookId());
			// ?????? ??????
			orderItem.setBookCount(oit.getBookCount());
			// ???????????? ??????
			orderItem.initSaleTotal();
			//List?????? ??????
			ords.add(orderItem);
		}
		/* OrderDTO ?????? */
		ord.setOrders(ords);
		ord.getOrderPriceInfo();
		
		/*DB ??????,????????????(,????????????) ??????*/
		
		/* orderId????????? ??? OrderDTO?????? orderId??? ?????? */
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
		String orderId = member.getMemberId() + format.format(date);
		ord.setOrderId(orderId);

		/* db?????? */
		dao.enrollOrder(ord);		//vam_order ??????
		for(OrderItemDTO oit : ord.getOrders()) {		//vam_orderItem ??????
			oit.setOrderId(orderId);
			dao.enrollOrderItem(oit);
	}
		/* ?????? ????????? ?????? ?????? */
		
		/* ?????? ?????? & ?????? ???(money) Member?????? ?????? */
		int calMoney = member.getMoney();
		calMoney -= ord.getOrderFinalSalePrice();
		member.setMoney(calMoney);
		
		/* ????????? ??????, ????????? ?????? & ?????? ?????????(point) Member?????? ?????? */
		int calPoint = member.getPoint();
		calPoint = calPoint - ord.getUsePoint() + ord.getOrderSavePoint();	// ?????? ????????? - ?????? ????????? + ?????? ?????????
		member.setPoint(calPoint);
		
		/* ?????? ???, ????????? DB ?????? */
		dao.deductMoney(member);
		
		/* ?????? ?????? ?????? */
		for(OrderItemDTO oit : ord.getOrders()) {
			/* ?????? ?????? ??? ????????? */
			BookVO book = bookservice.getGoodsInfo(oit.getBookId());
			book.setBookStock(book.getBookStock() - oit.getBookCount());
			/* ?????? ??? DB ?????? */
			dao.deductStock(book);

					}
		/* ???????????? ?????? */
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
	
	/*?????? ?????? */
	@Override
	@Transactional
	public void orderCancle(OrderCancelDTO dto) {
		/* ??????, ???????????? ?????? */
		/*??????*/
			MemberVO member = memberservice.getMemberInfo(dto.getMemberId());
		/*????????????*/
			List<OrderItemDTO> ords = dao.getOrderItemInfo(dto.getOrderId());
			for(OrderItemDTO ord : ords) {
				ord.initSaleTotal();
			}
		/* ?????? */
			OrderDTO orw = dao.getOrder(dto.getOrderId());
			orw.setOrders(ords);
			
			orw.getOrderPriceInfo();
		
			
	/* ???????????? ?????? DB */
			dao.orderCancle(dto.getOrderId());
			
			/* ???, ?????????, ?????? ?????? */
			/* ??? */
			int calMoney = member.getMoney();
			calMoney += orw.getOrderFinalSalePrice();
			member.setMoney(calMoney);
			
			/* ????????? */
			int calPoint = member.getPoint();
			calPoint = calPoint + orw.getUsePoint() - orw.getOrderSavePoint();
			member.setPoint(calPoint);
		

			/* DB?????? */
			dao.deductMoney(member);
			
			/* ?????? */
			for(OrderItemDTO ord : orw.getOrders()) {
				BookVO book = bookservice.getGoodsInfo(ord.getBookId());
				book.setBookStock(book.getBookStock() + ord.getBookCount());
				dao.deductStock(book);
			}
				}
	}
