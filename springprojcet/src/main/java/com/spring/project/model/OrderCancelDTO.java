package com.spring.project.model;

public class OrderCancelDTO {
	
	/*주문 취소 완료 후 패이지로 돌아가기 위해 기존 있던 
	 폐이지로 가기 위해서 페이징정보가 필요하기에 추가*/
	private String memberId;
	
	private String orderId;
	
	private String keyword;
	
	private int amount;
	
	private int pageNum;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	@Override
	public String toString() {
		return "OrderCancleDTO [memberId=" + memberId + ", orderId=" + orderId + ", keyword=" + keyword + ", amount="
				+ amount + ", pageNum=" + pageNum + "]";
	}
	
	
}
