package com.spring.project.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.model.CartDTO;
@Repository // 저장소 
public class CartDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	public int addCart(CartDTO cart) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("addCart",cart);
	}
	public CartDTO checkCart(CartDTO cart) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("checkCart",cart);
	}
	public List<CartDTO> getCart(String memberId) {
		return sqlSessionTemplate.selectList("getCart",memberId);
	}
	public int modifyCount(CartDTO cart) {
		return sqlSessionTemplate.update("modifyCount",cart);
	}
	public int deleteCart(int cartId) {
		return sqlSessionTemplate.delete("deleteCart",cartId);
	}
	public int deleteOrderCart(CartDTO dto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("deleteOrderCart",dto);
	}
}
