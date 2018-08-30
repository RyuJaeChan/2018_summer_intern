package com.nts.connect.pjt3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nts.connect.pjt3.dto.ProductImage;
import com.nts.connect.pjt3.dto.ProductThumbnail;
import com.nts.connect.pjt3.dto.result.ItemList;
import com.nts.connect.pjt3.service.ProductService;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
@RestController
@RequestMapping(path = "api")
public class ProductApiController {
	@Autowired
	private ProductService productService;

	private static final int LIMIT = 4;

	@GetMapping("/productThumbnails")
	public ItemList<ProductThumbnail> getProductThumbnails(
		@RequestParam(name = "start", required = false, defaultValue = "0") int start,
		@RequestParam(name = "categoryId", required = false, defaultValue = "0") int categoryId) {

		List<ProductThumbnail> items = productService.getProductThumbnailsByCategoryId(categoryId, start,
			LIMIT);
		long size = productService.getProductThumbnailCountByCategoryId(categoryId);

		ItemList<ProductThumbnail> resultJson = new ItemList<>();
		resultJson.setItems(items);
		resultJson.setSize(size);

		return resultJson;
	}

	@GetMapping("/productImages")
	public ItemList<ProductImage> getProductImages(@RequestParam(name = "id") int displayInfoId,
		@RequestParam(name = "type") String type) {
		List<ProductImage> items = productService.getProductImage(displayInfoId, type);

		ItemList<ProductImage> resultJson = new ItemList<>();
		resultJson.setItems(items);
		resultJson.setSize(items.size());

		return resultJson;
	}

}
