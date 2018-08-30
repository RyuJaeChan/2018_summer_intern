package com.nts.connect.pjt3.mapper;

import java.util.List;

import com.nts.connect.pjt3.dto.Category;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
public interface CategoryMapper {
	List<Category> selectCategoriesAll();
}
