package com.spring.project.service;

import com.spring.project.model.MemberVO;

public interface MemberSerivce {

   /*회원가입 */
	public int insert(MemberVO memberVo);
	
	/*아이디 중복 */
	public int idChk(String memberId) throws Exception;
	

    /* 로그인 */
    public MemberVO memberLogin(MemberVO member) throws Exception;

    /* 주문자 주소 정보 */
	public MemberVO getMemberInfo(String memberId);
}
