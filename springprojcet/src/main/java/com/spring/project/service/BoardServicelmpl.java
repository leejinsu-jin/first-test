package com.spring.project.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.dao.BoardDao;
import com.spring.project.model.BoardVO;

@Service
public class BoardServicelmpl implements BoardService{

	@Autowired
	BoardDao dao;
 public BoardServicelmpl() {
	 System.out.println("@Service 스프링 자동생성");
 }

 @Override
	public int insert(BoardVO BoardVO) {
		return dao.insert(BoardVO);
	}


}
