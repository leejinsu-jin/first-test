package com.spring.project.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.project.dao.BookDao;
import com.spring.project.model.AttachImageVO;
import com.spring.project.model.BookVO;
import com.spring.project.model.CateFilterVO;
import com.spring.project.model.Criteria;
import com.spring.project.model.PageDTO;
import com.spring.project.service.AdminService;
import com.spring.project.service.bookService;


@Controller
public class BookController {

	@Autowired
	BookDao dao;
	@Autowired
	bookService bookservice;
	@Autowired
	AdminService adminservice;
	//메인페이지 이동 
	@GetMapping("/main")
	public void mainPageGET(Model model) {
		System.out.println("메인페이지 진입");

		model.addAttribute("cate1", dao.getCateCode1());
		model.addAttribute("cate2", dao.getCateCode2());
	}
	@GetMapping("/main2")
	public void mainPageGET2(Model model) {
		System.out.println("메인페이지 진입");

	
	}

	/* 이미지 정보 반환 */
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttachImageVO>> getAttachList(int bookId){
		System.out.println("getAttachList 이미지 리스트 가져오기 북아이디"+bookId);
		return new ResponseEntity(dao.getAttachList(bookId),HttpStatus.OK);
	}
	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName){

		System.out.println("getImage() "+fileName);
		File file = new File("c:\\upload\\" + fileName);
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-type", Files.probeContentType(file.toPath()));

			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* 상품 검색 */

	@RequestMapping(value="/search", method = RequestMethod.GET)
	public String searchGoodsGET(Criteria cri, Model model, BookVO vo) {
		System.out.println("cri : " + cri);
		System.out.println("getGoodsList().......");
//		List<CateFilterVO> filterInfoList = new ArrayList<CateFilterVO>();
		String type = cri.getType();
		int AuthorId = cri.getAuthorId();
		String[] typeArr = type.split("");
		List<BookVO> AuthorArr = dao.getAuthorIdList(cri.getKeyword());

		if(type.equals("A") || type.equals("AC") || type.equals("AT") || type.equals("ACT")) {
			if(AuthorArr.equals(null)) {
				return "/search";
			}
		}
		for(String t : typeArr) {
			if(t.equals("A")||t.equals("T")) {
				cri.setAuthorArr(AuthorArr);
			}
		}

		List<BookVO> list =dao.getGoodsList(cri);

		list.forEach(book -> {

			int bookId = book.getBookId();

			List<AttachImageVO> imageList = dao.getAttachList(bookId);

			book.setImageList(imageList);

		});
		if(!list.isEmpty()) {
			model.addAttribute("list", list);

			System.out.println("list : " + list);

		} else {
			model.addAttribute("listcheck", "empty");
			return "/search";
		}
		model.addAttribute("pageMaker", new PageDTO(cri, dao.booksGetTotal(cri)));
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



//		System.out.println(dao.getCateList(cri));
//		System.out.println(filterInfoList);
//		model.addAttribute("filter_info",filterInfoList);
		return "/search";

	}
	/*상품 상세 */
	@GetMapping("/goodsDetail/{bookId}")
	public String goodDetailGet(@PathVariable("bookId")int bookId, Model model,AttachImageVO vo) {
		System.out.println("goodDetailGET()");
	
		
		model.addAttribute("goodsInfo",dao.getGoodsInfo(bookId));
		
	
		
		return "/goodsDetail";
		
	}
	/* 리뷰 쓰기 */
	@GetMapping("/replyEnroll/{memberId}")
	public String replyEnrollWindowGET(@PathVariable("memberId")String memberId, int bookId, Model model) {
		BookVO book = dao.getBookIdName(bookId);
		model.addAttribute("bookInfo", book);
		model.addAttribute("memberId", memberId);
		
		return "/replyEnroll";
	}
	
}

//	@GetMapping("/search/category")
//	public List<CateFilterVO> searchcateGoryGET (Criteria cri,CateFilterVO VO, Model model){
//	List<CateFilterVO> filterInfoList = new ArrayList<CateFilterVO>();
//	
//	String[] typeArr = cri.getType().split("");
//	List<BookVO> authorArr;
//	
//	for(String type : typeArr) {
//		if(type.equals("A")){
//			authorArr = dao.getAuthorIdList(cri.getKeyword());
//			cri.setAuthorArr(authorArr);
//		}
//	}
//	String[] cateList = dao.getCateList(cri);
//	String tempCateCode = cri.getCateCode();
//	
//	for(String cateCode : cateList) {
//		cri.setCateCode(cateCode);
//		List<CateFilterVO> filterInfo = dao.getCateInfo(cri);
//		filterInfoList.addAll(filterInfo);
//	}
//	cri.setCateCode(tempCateCode);
//	return dao.getCateInfo(cri);
//		}
//}

