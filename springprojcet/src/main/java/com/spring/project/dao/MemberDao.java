package com.spring.project.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.model.MemberVO;


@Repository // 저장소 
public class MemberDao {

	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	

	public MemberDao() {
		System.out.println("@MemberRepository 자동생성");	
	}
	
	public int insert(MemberVO memberVo) {		
		return sqlSessionTemplate.insert("member",memberVo);	
	}

	public int idChk(String memberId) {
	 return sqlSessionTemplate.selectOne(memberId);
	}
	public MemberVO memberLogin(MemberVO member) {		
		return sqlSessionTemplate.selectOne("memberLogin",member);	
	}
	public MemberVO getMemberInfo(String memberId){
		return sqlSessionTemplate.selectOne("getMemberInfo",memberId);
	}
}
