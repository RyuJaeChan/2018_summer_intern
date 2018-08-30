package com.nts.connect.pjt3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.ProductThumbnail;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
public interface ProductThumbnailMapper {
	List<ProductThumbnail> getProductThumbnailsByCategoryId(@Param("categoryId") int categoryId,
		@Param("start") int start, @Param("limit") int limit);

	long getProductThumbnailCountByCategoryId(@Param("categoryId") int categoryId);
}