package com.spring.project.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.model.BookVO;
import com.spring.project.model.MemberVO;
import com.spring.project.model.OrderDTO;
import com.spring.project.model.OrderItemDTO;
import com.spring.project.model.OrderPageItemDTO;

@Repository // 저장소 
public class OrderDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	
	public OrderPageItemDTO getGoodsInfos(int bookId) {
		return sqlSessionTemplate.selectOne("getGoodsInfos",bookId);
	}
		public int enrollOrder(OrderDTO ord) {
			// TODO Auto-generated method stub
			return sqlSessionTemplate.insert("enrollOrder",ord);
	
}				
		public OrderItemDTO getOrderInfos(int bookId) {
			return sqlSessionTemplate.selectOne("getOrderInfos",bookId);
			
		}
		public int enrollOrderItem(OrderItemDTO orid) {
			return sqlSessionTemplate.insert("enrollOrderItem",orid);
			
		}
		public int deductMoney(MemberVO member) {
			// TODO Auto-generated method stub
			return sqlSessionTemplate.update("deductMoney",member);
		}
		public int deductStock(BookVO book) {
			// TODO Auto-generated method stub
			return sqlSessionTemplate.update("deductStock",book);
		}
		public int orderCancle(String orderId) {
			// TODO Auto-generated method stub
			return sqlSessionTemplate.update("orderCancle",orderId);
		}
		public List<OrderItemDTO> getOrderItemInfo(String orderId) {
			// TODO Auto-generated method stub
			return sqlSessionTemplate.selectList("getOrderItemInfo",orderId);
		}
		public OrderDTO getOrder(String orderId) {
			// TODO Auto-generated method stub
			return sqlSessionTemplate.selectOne("getOrder",orderId);
		}
}