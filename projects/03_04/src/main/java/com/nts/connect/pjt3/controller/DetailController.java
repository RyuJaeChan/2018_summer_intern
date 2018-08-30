package com.nts.connect.pjt3.controller;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nts.connect.pjt3.dto.Product;
import com.nts.connect.pjt3.service.ProductService;
import com.nts.connect.pjt3.service.UserCommentService;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
@Controller
public class DetailController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserCommentService userCommentService;

	@GetMapping(path = "/detail")
	public String detailPage(@RequestParam(name = "id") Integer displayInfoid, ModelMap modelMap) {
		Product product = productService.getProductByDisplayInfoId(displayInfoid);
		BigDecimal score = userCommentService.getAverageScore(product.getId());
		long count = userCommentService.getCommentCount(product.getId());

		modelMap.addAttribute("product", product);
		modelMap.addAttribute("score", score);
		modelMap.addAttribute("count", count);
		modelMap.addAttribute(
			"localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy. MM. dd."));

		return "detail";
	}

}
