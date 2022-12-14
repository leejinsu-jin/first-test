package com.spring.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.dao.BookDao;
import com.spring.project.model.AttachImageVO;
@Service
public class  AttachServicelmpl implements AttachService {
	
	@Autowired
	BookDao dao;
	@Override
	public List<AttachImageVO> getAttachList(int bookId) {
		// TODO Auto-generated method stub
		return dao.getAttachList(bookId);
	}

	
}
