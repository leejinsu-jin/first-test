package com.spring.project.dao;



import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.model.BoardVO;

@Repository // 저장소 
public class BoardDao {


	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public BoardDao() {
		System.out.println("@Repository 자동생성");	
	}
	public int    insert(BoardVO BoardVO) {		
		return sqlSessionTemplate.insert("BoardVO.insert",BoardVO);	
		//	return SqlSessionTemplate.insert("BoardVO.insert",BoardVO);		
	}
	
}
