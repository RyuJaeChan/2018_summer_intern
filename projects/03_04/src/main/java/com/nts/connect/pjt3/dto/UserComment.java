package com.nts.connect.pjt3.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author	유재찬
 * @date	2018. 8. 14.
 */
public class UserComment {
	private int id;
	private int productId;
	private int resevationInfoId;
	String comment;
	String reservationEmail;
	LocalDateTime reservationDate;
	@NumberFormat(pattern = "0.00")
	private BigDecimal score;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	LocalDateTime modifyDate;

	public int getId() {
		return id;
	}

	public void setId(int userCommentId) {
		this.id = userCommentId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getResevationInfoId() {
		return resevationInfoId;
	}

	public void setResevationInfoId(int resevationInfoId) {
		this.resevationInfoId = resevationInfoId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public LocalDateTime getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDateTime reservationDate) {
		this.reservationDate = reservationDate;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(LocalDateTime modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "UserComment [id=" + id + ", productId=" + productId + ", resevationInfoId="
			+ resevationInfoId
			+ ", comment=" + comment + ", reservationEmail=" + reservationEmail + ", reservationDate=" + reservationDate
			+ ", score=" + score + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}

}
