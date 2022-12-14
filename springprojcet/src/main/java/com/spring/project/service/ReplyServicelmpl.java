package com.spring.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.dao.ReplyDao;
import com.spring.project.model.ReplyDTO;
@Service
public class ReplyServicelmpl implements ReplyService {
	
	@Autowired
	ReplyDao dao;
	/* 댓글 등록 */
	@Override
	public int enrollReply(ReplyDTO dto) {
		int result = dao.enrollReply(dto);
		return result;
	}
	
}
