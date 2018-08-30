package com.nts.connect.pjt3.mapper;

import org.apache.ibatis.annotations.Param;

import com.nts.connect.pjt3.dto.Product;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
public interface ProductMapper {
	Product selectProductByDisplayInfotId(@Param("displayInfoId") int displayInfoid);
}
