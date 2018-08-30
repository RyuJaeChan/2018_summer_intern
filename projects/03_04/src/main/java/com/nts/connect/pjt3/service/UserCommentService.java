package com.nts.connect.pjt3.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.UserComment;
import com.nts.connect.pjt3.mapper.UserCommentMapper;

/**
 * @author	유재찬
 * @date	2018. 8. 14.
 */
@Service
@Transactional(readOnly = true)
public class UserCommentService {
	@Autowired
	private UserCommentMapper userCommentMapper;

	public List<UserComment> getUserCommentList(int productId) {
		return userCommentMapper.selectUserCommentAll(productId);
	}

	public BigDecimal getAverageScore(int productId) {
		return userCommentMapper.selectAverageScore(productId);
	}

	public long getCommentCount(int productId) {
		return userCommentMapper.selectCommentCount(productId);
	}

}
