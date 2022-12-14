package com.spring.project.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.model.AttachImageVO;
import com.spring.project.model.AuthorVO;
import com.spring.project.model.Criteria;

@Repository // 저장소 
public class AuthorDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public AuthorDao() {
		System.out.println("@MemberRepository 자동생성");
	}
	
	public int authorEnroll(AuthorVO author) {
		//System.out.println(author);
		return sqlSessionTemplate.insert("authorEnroll",author);
	}
	public List<AuthorVO>  authorGetList(Criteria cri){
		return sqlSessionTemplate.selectList("authorGetList",cri);
		
	}
	public int authorGetTotal(Criteria cri) {
		return sqlSessionTemplate.selectOne("authorGetTotal",cri);
	}
	public AuthorVO authorGetDetail(int authorId) {
		return sqlSessionTemplate.selectOne("authorGetDetail",authorId);
		
	}
	public int authorModify(AuthorVO author) {
	
		return sqlSessionTemplate.update("authorModify",author);
	}
	public int authorDelete(int authorId) {
		return sqlSessionTemplate.delete("authorDelete", authorId);
	}	
	

	
}
