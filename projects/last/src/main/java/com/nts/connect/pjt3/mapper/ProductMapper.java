package com.nts.connect.pjt3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.Product;
import com.nts.connect.pjt3.dto.ProductImage;
import com.nts.connect.pjt3.dto.ProductThumbnail;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
public interface ProductMapper {
	Product selectProductWithCommentByDisplayInfotId(@Param("displayInfoId") int displayInfoId);

	Product selectProductWithPriceByDisplayInfotId(@Param("displayInfoId") int displayInfoId);

	Product selectProduct(@Param("productId") int id);

	List<ProductThumbnail> selectProductThumbnailsByCategoryId(@Param("categoryId") int categoryId,
		@Param("start") int start, @Param("limit") int limit);

	int selectProductThumbnailCountByCategoryId(@Param("categoryId") int categoryId);

	List<ProductImage> selectProductsImageByProductIdAndType(@Param("displayInfoId") int displayInfoId,
		@Param("type") String type);

}
