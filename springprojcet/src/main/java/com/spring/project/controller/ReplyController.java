package com.spring.project.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.project.dao.ReplyDao;
import com.spring.project.model.ReplyDTO;
import com.spring.project.service.ReplyService;

@RestController
public class ReplyController {
	@Autowired
	ReplyDao dao;
	@Autowired
	ReplyService service;
	/* 댓글 등록 */
	@PostMapping("/reply/enroll")
	public void enrollReplyPOST(ReplyDTO dto) {
		service.enrollReply(dto);
	}
}
