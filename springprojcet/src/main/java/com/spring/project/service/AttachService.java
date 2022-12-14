package com.spring.project.service;

import java.util.List;

import com.spring.project.model.AttachImageVO;

public interface AttachService {
//첨부파일 이미지 정보 봔환 요청 메서드 상품 상세페이지에서 이미지를 부르기 위한 것 
	/* 이미지 데이터 반환 */
	public List<AttachImageVO> getAttachList(int bookId);
}
