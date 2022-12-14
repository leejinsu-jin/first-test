package com.spring.project.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.spring.project.dao.MemberDao;
import com.spring.project.model.MemberVO;
@Service
public class MemberSerivceImpl implements MemberSerivce {
@Autowired
MemberDao dao;

public MemberSerivceImpl() {
	 System.out.println("@MemberService 스프링 자동생성");
}

@Override
public int insert(MemberVO memberVo) {
	// TODO Auto-generated method stub
	return dao.insert(memberVo);
}

@Override
public int idChk(String memberId) throws Exception {
	return dao.idChk(memberId);
}

@Override
public MemberVO memberLogin(MemberVO member) throws Exception  {
	return dao.memberLogin(member);

}

@Override
public MemberVO getMemberInfo(String memberId) {
	// TODO Auto-generated method stub
	return dao.getMemberInfo(memberId);
}



}


