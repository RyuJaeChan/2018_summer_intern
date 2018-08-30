package com.nts.connect.pjt3.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author	유재찬
 * @date	2018. 8. 20.
 */
public class ReservationInfo {
	private int id;
	private int productId;
	private String reservationName;
	private String reservationTel;
	private String reservationEmail;
	@JsonFormat(pattern = "yyyy. MM. dd.")
	private LocalDate reservationDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifyDate;
	private List<ReservationPrice> reservationPriceList;
	private long totalPrice;
	private int cancelFlag;
	private Product product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getReservationName() {
		return reservationName;
	}

	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	public String getReservationTel() {
		return reservationTel;
	}

	public void setReservationTel(String reservationTel) {
		this.reservationTel = reservationTel;
	}

	public String getReservationEmail() {
		return reservationEmail;
	}

	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}

	public LocalDate getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(LocalDate reservationDate) {
		this.reservationDate = reservationDate;
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

	public List<ReservationPrice> getReservationPriceList() {
		return reservationPriceList;
	}

	public void setReservationPriceList(List<ReservationPrice> reservationPriceList) {
		this.reservationPriceList = reservationPriceList;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(int cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ReservationInfo [id=" + id + ", productId=" + productId + ", reservationName=" + reservationName
			+ ", reservationTel=" + reservationTel + ", reservationEmail=" + reservationEmail + ", reservationDate="
			+ reservationDate + ", createDate=" + createDate + ", modifyDate=" + modifyDate + ", reservationPriceList="
			+ reservationPriceList + ", totalPrice=" + totalPrice + ", cancelFlag=" + cancelFlag + ", product="
			+ product + "]";
	}
}
