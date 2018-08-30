package com.nts.connect.pjt3.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.UserComment;

/**
 * @author	유재찬
 * @date	2018. 8. 14.
 */
public interface UserCommentMapper {
	List<UserComment> selectUserCommentAll(@Param("productId") int productId);

	BigDecimal selectAverageScore(@Param("productId") int productId);

	long selectCommentCount(@Param("productId") int productId);
}
