package com.nts.connect.pjt3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nts.connect.pjt3.dto.Category;
import com.nts.connect.pjt3.mapper.CategoryMapper;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
@Service
@Transactional(readOnly = true)
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	public List<Category> getCategories() {
		return categoryMapper.selectCategoriesAll();
	}

}
