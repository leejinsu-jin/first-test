package com.spring.project.service;

import java.util.List;

import com.spring.project.model.CartDTO;

public interface CartService {
	
	/* 카트 추가  table의 row를 추가 memberid,bookid,bookcount값 필요*/
	// 반환타입은 성공 실패 여부를 위해 int로 줌
	public int addCart(CartDTO cart) throws Exception;
	
	/* 카트 삭제 cart테이블의 지정한 row를 삭제*/
	//어떤 row를 삭제할지 선택 cartid에 대한 값 필요 변수는 int
	public int deleteCart(int cartId);
	
	/* 카트 수량 수정 cart row의  수량 변경 */
	//cartid와 bookCount 가 CartDTO 선언
	public int modifyCount(CartDTO cart);
	
	/* 카트 목록 Cart 지정한 회원 모든 row의 값을 가져오기 위해 */
	//어떤 회원인지 정보 필요 string입의 memberid를 선언
	//반환 타입은 한개 이상의 장바구니 데이터를 반환 받아야해서 List
	public List<CartDTO> getCart(String memberId);	
	
	/* 카트 확인  회원정보와 상품정보를 넘겨서 해당하는 row가 있는지 확인용*/
	// memberid,bookid를 한번에 넘기기 위해 CartDTO파라 선언 
	//조건에 맞는 row의 컬럼 값을 가져오도록 반환타입을 CartDTO 지정
	public CartDTO checkCart(CartDTO cart);
 
	/*장바구니 정보 리스트  */
	public List<CartDTO> getCartList(String memberId);

	/* 카트제거 (주문)회원이 장바구니에서 주문한 경우 장바구니 정보 삭제하기위한 것  */
	public int deleteOrderCart(CartDTO dto);
}
