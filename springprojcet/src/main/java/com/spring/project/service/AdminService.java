package com.spring.project.service;

import java.util.List;

import com.spring.project.model.AttachImageVO;
import com.spring.project.model.BookVO;
import com.spring.project.model.CateVO;
import com.spring.project.model.Criteria;
import com.spring.project.model.OrderDTO;

public interface AdminService {


	/* 상품 등록 */
	public void bookEnroll(BookVO book);	

	/* 카테고리 리스트 */
	public List<CateVO> cateList();

	/*상품 리스트 */
	public List<BookVO> goodsGetList(Criteria cri) throws Exception;

	/*상품 총 개수 */
	public int goodsGetTotal(Criteria cri) throws Exception;

	/* 상품 조회 페이지 */
	public BookVO goodsGetDetail(int bookId) throws Exception;

	/*상품 수정  */
	public int goodsModify(BookVO VO);
	
	/*상품 정보 삭제 */
	public int goodsDelete(int bookId);
	
	/*이미지 등록  */
	public void imageEnroll(AttachImageVO vo);
	
	/* 지정 상품 이미지 전체 삭제 */
	public int deleteImageAll(int bookId);
	
	/* 어제자 날짜 이미지 리스트 */
	public List<AttachImageVO> checkFileList();
	
	/* 지정 상품 이미지 정보 얻기 */
	public List<AttachImageVO> getAttachInfo(int bookId);

	/*주문 상품 리스트 */
	public List<OrderDTO> getOrderList(Criteria cri);
	
	/* 주문 총 갯수 */
	public int getOrderTotal(Criteria cri);
}
