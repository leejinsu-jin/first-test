package com.spring.project.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.project.model.AttachImageVO;
import com.spring.project.model.AuthorVO;
import com.spring.project.model.BookVO;
import com.spring.project.model.Criteria;
import com.spring.project.model.OrderCancelDTO;
import com.spring.project.model.OrderDTO;
import com.spring.project.model.PageDTO;
import com.spring.project.service.AdminService;
import com.spring.project.service.AuthorService;
import com.spring.project.service.OrderService;


@Controller
public class AdminController {

	@Autowired
	AuthorService authorService;
	@Autowired
	AdminService adminservice;
	@Autowired
	OrderService orderservice;

	/* 관리자 메인 페이지 이동 */
	@RequestMapping(value="/admin/main", method = RequestMethod.GET)
	public void adminMainGET() throws Exception{

		System.out.println("관리자 페이지 이동");
	}
	/* 상품 관리 페이지 접속 */
	@RequestMapping(value = "/admin/goodsManage", method = RequestMethod.GET)
	public void goodsManageGET(Criteria cri, Model model) throws Exception{
		System.out.println("상품 관리(상품 목록) 페이지 접속");

		/* 상품 리스트 데이터 */
		List list = adminservice.goodsGetList(cri);
		System.out.println(list);

		if(!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}

		/* 페이지 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, adminservice.goodsGetTotal(cri)));
	}

	/* 상품 등록 페이지 접속 */
	@RequestMapping(value = "/admin/goodsEnroll", method = RequestMethod.GET)
	public void goodsEnrollGET(Model model) throws Exception{
		System.out.println("상품 등록 페이지 접속");
		
		ObjectMapper objm= new ObjectMapper();
		List list = adminservice.cateList();

		String cateList = objm.writeValueAsString(list);
		model.addAttribute("cateList", cateList);
		System.out.println("카테고리 List 변경 전 "+list);
		System.out.println("string으로 변경 후"+cateList);
	}
	/* 상품 등록 페이지 접속 */
	@PostMapping("/admin/goodsEnroll")
	public String goodsEnrollPOST(BookVO book, RedirectAttributes rttr) {
		System.out.println("goodsEnrollPOST" +book);
		adminservice.bookEnroll(book);
		rttr.addFlashAttribute("enroll_result",book.getBookName());
		return "redirect:/admin/goodsManage";
	}
	/* 첨부 파일 업로드 */
	@PostMapping("/admin/uploadAjaxAction")
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) {
		/* 이미지 파일 체크 */
		for(MultipartFile multipartFile: uploadFile) {
			
			File checkfile = new File(multipartFile.getOriginalFilename());
			String type = null;
			try {
				type = Files.probeContentType(checkfile.toPath());
				System.out.println("MIME TYPE : " + type);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(!type.startsWith("image")) {
				List<AttachImageVO> list = null;
				return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
			}
			
		}// for
		
		String uploadFolder = "C:\\upload";
		
		// 날짜 폴더 경로
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		
		/* 폴더 생성 */
		File uploadPath = new File(uploadFolder, datePath);

		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		List<AttachImageVO> list = new ArrayList();

		for(MultipartFile multipartFile : uploadFile) {
			// 이미지 저장 객체
			AttachImageVO vo = new AttachImageVO();

			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename();
			vo.setFileName(uploadFileName);
			vo.setUploadPath(datePath);

			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString();
			vo.setUuid(uuid);

			uploadFileName = uuid + "_" + uploadFileName;

			/* 파일 위치, 파일 이름을 합친 File 객체 */
			File saveFile = new File(uploadPath, uploadFileName);

			/* 파일 저장 */
			try {
				multipartFile.transferTo(saveFile);

				//썸네일 생성

				File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
				BufferedImage bo_image = ImageIO.read(saveFile);
				// 비율 
				double ratio = 3;
				// 넓이 높이
				int width = (int) (bo_image.getWidth() / ratio);
				int height = (int) (bo_image.getHeight() / ratio);
				BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

				Graphics2D graphic = bt_image.createGraphics();
				graphic.drawImage(bo_image, 0, 0,width,height, null);

			

				ImageIO.write(bt_image, "jpg", thumbnailFile);

			} catch (Exception e) {
				e.printStackTrace();
			} 
			list.add(vo);
		}// for
		ResponseEntity<List<AttachImageVO>> result = 
				new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);
		return result;
	}
		
	/* 이미지 파일 삭제 */
	@PostMapping("/admin/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName){
		
		System.out.println("deleteFile........" + fileName);
		
		File file = null;
		
	try {
		file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
		file.delete();
		
		/* 원본 파일 삭제 */
		String originFileName = file.getAbsolutePath().replace("s_", "");
		
		System.out.println("originFileName : " + originFileName);
		file = new File(originFileName);
		
		file.delete();
	} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
		
	}
	/*상품 조회 (수정)폐이지 */
	@GetMapping({"/admin/goodsDetail","/admin/goodsModify"})
	public void goodGetInfoGET(int bookId, Criteria cri, Model model) throws Exception  {
		System.out.println("책 pk"+bookId);
		//json으로 데이터 담기 전체 데이터
		ObjectMapper objm= new ObjectMapper();
		model.addAttribute("cateList", objm.writeValueAsString(adminservice.cateList()));

		/* 목록 폐이지 조건 정보 */
		model.addAttribute("cri",cri);

		/* 조회 페이지 정보 */
		model.addAttribute("goodsInfo",adminservice.goodsGetDetail(bookId));
	}

	/*상품 정보 수정*/
	@PostMapping("/admin/goodsModify")
	public String goodsModifyPOST(BookVO VO, RedirectAttributes rttr) {
		System.out.println(VO);

		int result = adminservice.goodsModify(VO);
		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/goodsManage";

	}

	/*상품 정보 삭제 */
	@PostMapping("/admin/goodsDelete")
	public String goodsDeletePOST(int bookId, RedirectAttributes rttr) {
		 System.out.println(bookId);
		 /*list를 filelist객체에 담기위함*/
		 List<AttachImageVO> fileList = adminservice.getAttachInfo(bookId);
		 if(fileList != null) {
			 List<Path> pathList = new ArrayList();
				fileList.forEach(vo ->{
					// 원본 이미지
					Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
					pathList.add(path);
					// 섬네일 이미지
					path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" + vo.getUuid()+"_" + vo.getFileName());
					pathList.add(path);
				});
				pathList.forEach(path ->{
					path.toFile().delete();
				});

			}
		int result = adminservice.goodsDelete(bookId);
		rttr.addFlashAttribute("delete_result",result);
		return "redirect:/admin/goodsManage";
	}
	/*상품 등록의 작가 검색 팝업창 */
	@GetMapping("/admin/authorPop")
	public void authorPopGET(Criteria cri, Model model) throws Exception{
		System.out.println("작가 검색 페이지 이동");	
		cri.setAmount(5);
		List list = authorService.authorGetList(cri);
		System.out.println(cri);
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}

		/* 페이지 이동 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));
		System.out.println(cri);
	}


	/* 작가 등록 페이지 접속 */
	@RequestMapping(value = "/admin/authorEnroll", method = RequestMethod.GET)
	public void authorEnrollGET() throws Exception{
		System.out.println("작가 등록 페이지 접속");
	}

	/*작가 등록 페이지 */
	@RequestMapping(value="/admin/authorEnroll.do", method = RequestMethod.POST)
	public String authorEnrollPOST( AuthorVO author, RedirectAttributes rttr) throws Exception{
		System.out.println("authorEnroll" +author);

		authorService.authorEnroll(author); //작가 등록 쿼리 실행
		rttr.addFlashAttribute("enroll_result", author.getAuthorName()); //등록 성공 메세지(작가 이름)
		System.out.println(author.getAuthorName());

		return "redirect:/admin/authorManage";

	}
	/* 작가 관리 페이지 접속 */
	@RequestMapping(value = "/admin/authorManage", method = RequestMethod.GET)

	public void authorManageGET(Criteria cri, Model model) throws Exception{

		System.out.println(cri);
		List list = authorService.authorGetList(cri);

		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}

		/* 페이지 이동 인터페이스 데이터 */
		int total = authorService.authorGetTotal(cri);
		//  System.out.println(total);

		PageDTO pageMaker = new PageDTO(cri, total);
		System.out.println(pageMaker);
		model.addAttribute("pageMaker", pageMaker);
	}
	/*작가 관리/ 작가 상세 페이지 */
	@GetMapping({"/admin/authorDetail","/admin/authorModify"})
	public void authorGetInfoGET(int authorId, Criteria cri, Model model) throws Exception{
		System.out.println(authorId);
		/* 작가 관리 페이지 정보 */
		model.addAttribute("cri", cri);

		/* 선택 작가 정보 */
		model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));
	}

	/* 작가 정보 수정 */

	@PostMapping("/admin/authorModify")
	public String authorModifyPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{

		//System.out.println("작가수정"+author);

		int result = authorService.authorModify(author);
		rttr.addFlashAttribute("name",author.getAuthorName());
		//System.out.println(result);
		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/authorManage";

	}

	/* 작가 정보 삭제 */
	@PostMapping("/admin/authorDelete")
	public String authorDeletePOST(int authorId, RedirectAttributes rttr) {
		System.out.println("authorDeletePOST");
		int result = 0; 

		try {
			result = authorService.authorDelete(authorId);
		}catch(Exception e) {
			e.printStackTrace();
			result =2; 
			rttr.addFlashAttribute("delete_result",result);

			return "redirect:/admin/authorManage";
		}

		rttr.addFlashAttribute("delete_result", result);

		return "redirect:/admin/authorManage";

	}
	
	/*주문 현황 페이지 */
	@GetMapping("/admin/orderList")
	public String orderListGet(Criteria cri, Model model) {
List<OrderDTO> list = adminservice.getOrderList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
			model.addAttribute("pageMaker", new PageDTO(cri, adminservice.getOrderTotal(cri)));
		} else {
			model.addAttribute("listCheck", "empty");
		}
		
		return "/admin/orderList";
		
	}
	/*주문 삭제 */
	@PostMapping("/admin/orderCancle")
	public String orderCanclePOST(OrderCancelDTO dto) {
		orderservice.orderCancle(dto);
		return "redirect:/admin/orderList?keyword=" + dto.getKeyword() + "&amount=" + dto.getAmount() + "&pageNum=" + dto.getPageNum();
	}
}



