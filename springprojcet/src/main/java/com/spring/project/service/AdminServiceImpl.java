package com.spring.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.project.dao.BookDao;
import com.spring.project.model.AttachImageVO;
import com.spring.project.model.BookVO;
import com.spring.project.model.CateVO;
import com.spring.project.model.Criteria;
import com.spring.project.model.OrderDTO;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	
	
	
	@Autowired
	BookDao dao;
	@Override
	public void bookEnroll(BookVO book) {
		dao.bookEnroll(book);
		//이미지 체크 
		if(book.getImageList() == null || book.getImageList().size() <= 0) {
			return;
		}
		
			book.getImageList().forEach(attach ->{
			attach.setBookId(book.getBookId());
			dao.imageEnroll(attach);
		});
	
	}
	@Override
	public List<CateVO> cateList() {
		return dao.cateList();
	}
	@Override
	public List<BookVO> goodsGetList(Criteria cri) throws Exception {
		return dao.goodsGetList(cri);
	}
	@Override
	public int goodsGetTotal(Criteria cri) throws Exception {
		return dao.goodsGetTotal(cri);
	}
	@Override
	public BookVO goodsGetDetail(int bookId) throws Exception {
		return dao.goodsGetDetail(bookId);
	}
	@Override
	public int goodsModify(BookVO VO) {
		
		int result = dao.goodsModify(VO);
		
		if(result == 1 && VO.getImageList() != null && VO.getImageList().size() > 0) {
			dao.deleteImageAll(VO.getBookId());
			
			VO.getImageList().forEach(attach -> {
				attach.setBookId(VO.getBookId());
				dao.imageEnroll(attach);
			});
		}
		
		return result;
	}
	@Override
	 @Transactional
	public int goodsDelete(int bookId) {
		dao.deleteImageAll(bookId);
		return dao.goodsDelete(bookId);
	}
	@Override
	public void imageEnroll(AttachImageVO vo) {
		 dao.imageEnroll(vo);
		
	}
	@Override
	public int deleteImageAll(int bookId) {
		return dao.deleteImageAll(bookId);
	}
	@Override
	public List<AttachImageVO> checkFileList() {
		 return dao.checkFileList();
	}
	@Override
	public List<AttachImageVO> getAttachInfo(int bookId) {
		return dao.getAttachInfo(bookId);
	}
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {
		// TODO Auto-generated method stub
		return dao.getOrderList(cri);
	}
	@Override
	public int getOrderTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return dao.getOrderTotal(cri);
	}

	
	
}
