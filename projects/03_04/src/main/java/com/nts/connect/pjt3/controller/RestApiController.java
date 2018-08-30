package com.nts.connect.pjt3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nts.connect.pjt3.dto.Category;
import com.nts.connect.pjt3.dto.Promotion;
import com.nts.connect.pjt3.dto.result.ItemList;
import com.nts.connect.pjt3.service.CategoryService;
import com.nts.connect.pjt3.service.PromotionService;

/**
 * @author	유재찬
 * @date	2018. 8. 7.
 */
@RestController
@RequestMapping(path = "api")
public class RestApiController {
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public ItemList<Category> categories() {
		List<Category> items = categoryService.getCategories();
		long size = categoryService.getCategoryCount();

		ItemList<Category> resultJson = new ItemList<>();
		resultJson.setItems(items);
		resultJson.setSize(size);

		return resultJson;
	}

	@GetMapping("/promotions")
	public ItemList<Promotion> getPromotions() {
		List<Promotion> items = promotionService.getPromotions();
		long size = promotionService.getPromotionCount();

		ItemList<Promotion> resultJson = new ItemList<>();
		resultJson.setItems(items);
		resultJson.setSize(size);

		return resultJson;
	}

}
