package com.spring.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.dao.AuthorDao;
import com.spring.project.model.AuthorVO;
import com.spring.project.model.Criteria;

@Service
public class AuthorServiceImpl  implements AuthorService{

	@Autowired
	AuthorDao dao;

	@Override
	public void authorEnroll(AuthorVO author) throws Exception {
		dao.authorEnroll(author);
		
	}

	@Override
	public List<AuthorVO> authorGetList(Criteria cri) throws Exception {
		return dao.authorGetList(cri);
	}

	@Override
	public int authorGetTotal(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.authorGetTotal(cri);
	}

	@Override
	public AuthorVO authorGetDetail(int authorId) throws Exception {
		// TODO Auto-generated method stub
		return dao.authorGetDetail(authorId);
	}

	@Override
	public int authorModify(AuthorVO author) throws Exception {
		// TODO Auto-generated method stub
		return dao.authorModify(author);
	}

	@Override
	public int authorDelete(int authorId) {
		// TODO Auto-generated method stub
		return dao.authorDelete(authorId);
	}	
	}

