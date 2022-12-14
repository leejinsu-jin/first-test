package com.spring.project.service;

import com.spring.project.model.ReplyDTO;

public interface ReplyService {
	/* 댓글 등록 */
	public int enrollReply(ReplyDTO dto);
}
