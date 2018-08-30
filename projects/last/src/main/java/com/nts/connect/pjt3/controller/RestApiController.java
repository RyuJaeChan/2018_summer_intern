package com.nts.connect.pjt3.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nts.connect.pjt3.dto.Category;
import com.nts.connect.pjt3.dto.Promotion;
import com.nts.connect.pjt3.dto.ReservationInfo;
import com.nts.connect.pjt3.dto.UserComment;
import com.nts.connect.pjt3.dto.result.ItemList;
import com.nts.connect.pjt3.dto.result.ResultJson;
import com.nts.connect.pjt3.service.CategoryService;
import com.nts.connect.pjt3.service.PromotionService;
import com.nts.connect.pjt3.service.ReservationService;
import com.nts.connect.pjt3.service.UserCommentService;

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
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private UserCommentService userCommentService;

	@GetMapping("/categories")
	public ResultJson<?> categories() {
		List<Category> items = categoryService.getCategories();

		return ResultJson.builder()
			.setResult("success")
			.setBody(
				ItemList.builder()
					.setItems(items)
					.setSize(items.size())
					.build())
			.build();
	}

	@GetMapping("/promotions")
	public ResultJson<?> getPromotions() {
		List<Promotion> items = promotionService.getPromotions();

		return ResultJson.builder()
			.setResult("success")
			.setBody(
				ItemList.builder()
					.setItems(items)
					.setSize(items.size())
					.build())
			.build();
	}

	@PostMapping(value = "/reserve", consumes = "application/json")
	public ReservationInfo reserve(@RequestBody ReservationInfo reservation) {
		int reservationInfoId = reservationService.insertReservation(reservation);

		return reservationService.getReservationInfo(reservationInfoId);
	}

	@PutMapping(value = "/reserve", consumes = "application/json")
	public ReservationInfo cancelReservation(@RequestBody ReservationInfo reservation) {
		reservationService.cancelReservationInfo(reservation.getId());
		return reservation;
	}

	@PostMapping("/comments")
	public UserComment registerComment(UserComment userComment,
		@RequestParam(value = "imageFile", required = false) List<MultipartFile> imageFile)
		throws IOException {

		userCommentService.insertComment(userComment, imageFile);

		return userComment;
	}

	@GetMapping(value = "/comments/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] commentImage(@PathVariable int id) throws IOException {
		UserComment userComment = userCommentService.getUserComment(id);
		String saveFileName = userComment.getUserCommentImageList().get(0).getSaveFileName();

		InputStream is = new FileInputStream(saveFileName);

		return IOUtils.toByteArray(is);
	}

}
