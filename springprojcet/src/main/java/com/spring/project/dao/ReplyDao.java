package com.spring.project.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.project.model.ReplyDTO;

@Repository // 저장소 
public class ReplyDao {
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	
	public int enrollReply(ReplyDTO dto) {
		return sqlSessionTemplate.insert("enrollReply",dto);
	}
}
