package com.nts.connect.pjt3.controller;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nts.connect.pjt3.dto.UserComment;
import com.nts.connect.pjt3.service.UserCommentService;

/**
 * @author	유재찬
 * @date	2018. 8. 14.
 */
@Controller
public class ReviewController {
	@Autowired
	private UserCommentService userCommentService;

	@GetMapping(path = "/review")
	public String reviewPage(@RequestParam(name = "id") Integer productId, ModelMap modelMap) {
		List<UserComment> commentList = userCommentService.getUserCommentList(productId);
		BigDecimal score = userCommentService.getAverageScore(productId);
		long count = userCommentService.getCommentCount(productId);

		modelMap.addAttribute("productId", productId);
		modelMap.addAttribute("commentList", commentList);
		modelMap.addAttribute("score", score);
		modelMap.addAttribute("count", count);
		modelMap.addAttribute(
			"localDateTimeFormat", DateTimeFormatter.ofPattern("yyyy. MM. dd."));

		return "review";
	}

}
