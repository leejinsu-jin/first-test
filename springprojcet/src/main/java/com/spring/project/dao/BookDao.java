package com.spring.project.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.model.AttachImageVO;
import com.spring.project.model.AuthorVO;
import com.spring.project.model.BookVO;
import com.spring.project.model.CateFilterVO;
import com.spring.project.model.CateVO;
import com.spring.project.model.Criteria;
import com.spring.project.model.OrderDTO;

@Repository // 저장소 
public class BookDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public BookDao() {
		System.out.println("@Repository 상품  자동생성");	
	}
	public int bookEnroll(BookVO book) {
		return sqlSessionTemplate.insert("bookEnroll",book);
	}
	public List<CateVO> cateList(){
		return sqlSessionTemplate.selectList("cateList");

	}
	public List<BookVO> goodsGetList(Criteria cri){
		return sqlSessionTemplate.selectList("goodsGetList",cri);

	}
	public int goodsGetTotal(Criteria cri) {
		return sqlSessionTemplate.selectOne("goodsGetTotal",cri);
	}
	public BookVO goodsGetDetail(int bookId) {
		return sqlSessionTemplate.selectOne("goodsGetDetail",bookId);
	}
	public int goodsModify(BookVO VO){
		return sqlSessionTemplate.update("goodsModify",VO);
	}
	public int goodsDelete(int bookId) {
		return sqlSessionTemplate.delete("goodsDelete",bookId);
	}
	public int imageEnroll(AttachImageVO attach) {
		return sqlSessionTemplate.insert("imageEnroll",attach);
	}
	public List<AttachImageVO> getAttachList(int bookId){
		return sqlSessionTemplate.selectList("getAttachList", bookId);
	}
	public int deleteImageAll(int bookId) {
		return sqlSessionTemplate.delete("deleteImageAll", bookId);
	}
	public List<AttachImageVO> checkFileList(){
		return sqlSessionTemplate.selectList("checkFileList");
	}
	public List<AttachImageVO> getAttachInfo(int bookId){
		return sqlSessionTemplate.selectList("getAttachInfo",bookId);
	}
	public List<BookVO> getGoodsList(Criteria cri){
		return sqlSessionTemplate.selectList("getGoodsList", cri);
	}
	public int booksGetTotal(Criteria cri) {
		return sqlSessionTemplate.selectOne("booksGetTotal",cri);
	}
	/* 작가 id 리스트 요청 */
	public List<BookVO> getAuthorIdList(String keyword) {
		return sqlSessionTemplate.selectList("getAuthorIdList", keyword);
	}
	/* 국내 카테고리 리스트 */
	public List<CateVO> getCateCode1(){
		return sqlSessionTemplate.selectList("getCateCode1");
	}
	/* 외국 카테고리 리스트 */
	public List<CateVO> getCateCode2(){
		return sqlSessionTemplate.selectList("getCateCode2");
	}
	
	public String[] getCateList(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getCateList");
	}
////	/* 카테고리 정보(+검색 대상 갯수) */
//	public List<CateFilterVO> getCateInfoList(Criteria cri) {
//		// TODO Auto-generated method stub
//		return sqlSessionTemplate.selectList("getCateInfoList");
//	}
	public CateFilterVO getCateInfo(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getCateInfo");
	}
	
	public BookVO getGoodsInfo(int bookId) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getGoodsInfo",bookId);
}
	public List<OrderDTO> getOrderList(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("getOrderList",cri);
	}
	public int getOrderTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getOrderTotal",cri);
	}
	
	public BookVO getBookIdName(int bookId) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getBookIdName",bookId);
}
}