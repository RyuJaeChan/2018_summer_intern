package com.nts.connect.pjt3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.ProductImage;

public interface ProductImageMapper {
	public List<ProductImage> selectProductsImageByProductIdAndType(@Param("displayInfoId") int displayInfoId,
		@Param("type") String type);

	public long selectProductImageCount(@Param("displayInfoId") int displayInfoid, @Param("type") String type);
}
