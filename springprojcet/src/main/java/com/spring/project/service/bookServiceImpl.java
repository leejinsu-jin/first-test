package com.spring.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.dao.BookDao;
import com.spring.project.model.BookVO;
import com.spring.project.model.CateFilterVO;
import com.spring.project.model.CateVO;
import com.spring.project.model.Criteria;

@Service
public class bookServiceImpl implements bookService {

	@Autowired
	BookDao dao;
	@Autowired
	bookService bookservice;
	@Autowired
	AdminService adminservice;
	//상품 총 갯수
	@Override
	public int booksGetTotal(Criteria cri) {
		return dao.booksGetTotal(cri);
	}
	/* 상품 검색 */
	@Override
	public List<BookVO> getBooksList(Criteria cri) {
		return dao.getGoodsList(cri);
	}
	@Override
	public List<BookVO> getAuthorIdList(String keyword) {
		return dao.getAuthorIdList("getAuthorIdList");
	}
	@Override
	public List<CateVO> getCateCode1() {
		// TODO Auto-generated method stub
		return dao.getCateCode1();
	}
	@Override
	public List<CateVO> getCateCode2() {
		// TODO Auto-generated method stub
		return dao.getCateCode2();
	}
	@Override
	public BookVO getGoodsInfo(int bookId) {
		BookVO goodsInfo = dao.getGoodsInfo(bookId);
		goodsInfo.setImageList(dao.getAttachInfo(bookId));
		
		return dao.getGoodsInfo(bookId);
	}
	@Override
	public BookVO getBookIdName(int bookId) {
		// TODO Auto-generated method stub
		return dao.getBookIdName(bookId);
	}

//	@Override
//	public List<CateFilterVO> getCateInfoList(Criteria cri) {
//    List<CateFilterVO> filterInfoList = new ArrayList<CateFilterVO>();
//		
//		String[] typeArr = cri.getType().split("");
//		List<BookVO> authorArr;
		
//		for(String type : typeArr) {
//			if(type.equals("A")){
//				authorArr = dao.getAuthorIdList(cri.getKeyword());
//				if(authorArr.equals(null)) {
//					return filterInfoList;
//				}
//				cri.setAuthorArr(authorArr);
//			}
//		}
//		
//		String[] cateList = dao.getCateList(cri);
//		
//		String tempCateCode = cri.getCateCode();
//		
//		for(String cateCode : cateList) {
//			cri.setCateCode(cateCode);
//			CateFilterVO filterInfo = dao.getCateInfo(cri);
//			filterInfoList.add(filterInfo);
//		}
//		
//		cri.setCateCode(tempCateCode);
//		
//		return filterInfoList;
//		
//		
	
//	}

//	@Override
//	public String[] getCateList(Criteria cri) {
//		// TODO Auto-generated method stub
//		return dao.getCateList(cri);
//	}
//	@Override
//	/* 카테고리 정보(+검색 대상 갯수) */
//	public CateFilterVO getCateInfo(Criteria cri) {
//		// TODO Auto-generated method stub
//		return dao.getCateInfo(cri);
//	}

}
