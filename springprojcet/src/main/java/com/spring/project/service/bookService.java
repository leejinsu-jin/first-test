package com.spring.project.service;

import java.util.List;

import com.spring.project.model.BookVO;
import com.spring.project.model.CateFilterVO;
import com.spring.project.model.CateVO;
import com.spring.project.model.Criteria;

public interface bookService {
	/* 상품 검색 */
	public List<BookVO> getBooksList(Criteria cri);
	
	/* 상품 총 갯수 */
	public int booksGetTotal(Criteria cri);
	
	/* 작가 id 리스트 요청 */
	public List<BookVO> getAuthorIdList(String keyword);
	
	/*국내 카테고리 리스트  */
	public List<CateVO> getCateCode1();
	
	/*외국 카테고리 리스트  */
	public List<CateVO> getCateCode2();
	
//////	/* 검색 대상 카테고리 리스트 */
//	public String[] getCateList(Criteria cri);
//	
//////	/* 카테고리 정보(+검색 대상 갯수) */
//	public CateFilterVO getCateInfo(Criteria cri);
//////	
//////	/*검색 결과 카테고리 필터 정보 */
//	public List<CateFilterVO> getCateInfoList(Criteria cri);
//	
	
	/* 상품 정보 */
	public BookVO getGoodsInfo(int bookId);
	
	/*상품 id 이름 */
	public BookVO getBookIdName(int bookId);
}
