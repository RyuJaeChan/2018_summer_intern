package com.nts.connect.pjt3.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nts.connect.pjt3.dto.Product;
import com.nts.connect.pjt3.dto.ReservationInfo;
import com.nts.connect.pjt3.dto.UserComment;
import com.nts.connect.pjt3.enumeration.ReservationType;
import com.nts.connect.pjt3.service.ProductService;
import com.nts.connect.pjt3.service.ReservationService;
import com.nts.connect.pjt3.service.UserCommentService;

/**
 * @author	유재찬
 * @date	2018. 8. 20.
 */
@Controller
public class ViewController {
	@Autowired
	private UserCommentService userCommentService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReservationService reservationService;

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy. MM. dd.");

	@GetMapping("/")
	public String mainPage(@CookieValue(value = "email", required = false) String email, ModelMap modelMap) {
		modelMap.addAttribute("userEmail", email);

		return "mainpage";
	}

	@GetMapping("/detail")
	public String detailPage(@CookieValue(value = "email", required = false) String email,
		@RequestParam(name = "id") Integer displayInfoid,
		ModelMap modelMap) {
		Product product = productService.getProductByDisplayInfoId(displayInfoid);
		BigDecimal score = userCommentService.getAverageScore(product.getId());
		long count = userCommentService.getCommentCount(product.getId());

		modelMap.addAttribute("userEmail", email);
		modelMap.addAttribute("product", product);
		modelMap.addAttribute("score", score);
		modelMap.addAttribute("count", count);
		modelMap.addAttribute("localDateTimeFormat", dateTimeFormatter);

		return "detail";
	}

	@GetMapping("/reserve")
	public String reservationPage(@RequestParam(name = "id") Integer productId, ModelMap modelMap) {
		Product product = productService.getProductWithPriceByDisplayInfoId(productId);

		modelMap.addAttribute("product", product);

		modelMap.addAttribute("reservationDate",
			dateTimeFormatter.format(LocalDate.now().plusDays(5)));

		return "reserve";
	}

	@GetMapping("/review")
	public String reviewPage(@RequestParam(name = "id") Integer productId, ModelMap modelMap) {
		List<UserComment> commentList = userCommentService.getUserCommentList(productId);
		BigDecimal score = userCommentService.getAverageScore(productId);
		long count = userCommentService.getCommentCount(productId);

		modelMap.addAttribute("productId", productId);
		modelMap.addAttribute("commentList", commentList);
		modelMap.addAttribute("score", score);
		modelMap.addAttribute("count", count);
		modelMap.addAttribute("localDateTimeFormat", dateTimeFormatter);

		return "review";
	}

	@GetMapping("/login")
	public String login(HttpServletResponse response, @RequestParam(name = "resrv_email") String email) {
		Cookie setCookie = new Cookie("email", email);
		setCookie.setMaxAge(-1);
		response.addCookie(setCookie);

		return "redirect:/myreservation";
	}

	@GetMapping("/myreservation")
	public String myreservationPage(@CookieValue(value = "email", required = false) String email, ModelMap modelMap) {
		if (email == null) {
			return "redirect:/bookinglogin";
		}

		List<ReservationInfo> confirmedList = reservationService
			.getReservationInfoByEmail(ReservationType.Confirmed, email);
		List<ReservationInfo> usedList = reservationService.getReservationInfoByEmail(ReservationType.Used, email);
		List<ReservationInfo> canceledList = reservationService.getReservationInfoByEmail(ReservationType.Canceled,
			email);

		modelMap.addAttribute("confirmedList", confirmedList);
		modelMap.addAttribute("usedList", usedList);
		modelMap.addAttribute("canceledList", canceledList);
		modelMap.addAttribute("localDateTimeFormat", dateTimeFormatter);

		return "myreservation";
	}

	@GetMapping("/comment")
	public String comment(@CookieValue String email,
		@RequestParam int id, ModelMap modelMap) {
		if (email == null) {
			return "redirect:/bookinglogin";
		}

		ReservationInfo reservationInfo = reservationService.getReservationInfo(id);

		modelMap.addAttribute("reservationInfo", reservationInfo);
		reservationInfo.getProduct().getId();
		return "reviewWrite";
	}

}
