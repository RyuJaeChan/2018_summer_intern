package com.nts.connect.pjt3.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductPrice {
	private int id;
	private int productId;
	private PriceType priceTypeName;
	private int price;
	@NumberFormat(pattern = "00.00")
	private BigDecimal discountRate;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime modifyDate;

	enum PriceType {
		A("성인"),
		B("유아"),
		D("D석"),
		E("E석"),
		R("R석"),
		S("S석"),
		V("V석"),
		Y("청소년");

		private final String typeName;

		PriceType(final String typeName) {
			this.typeName = typeName;
		}

		@Override
		public String toString() {
			return this.typeName;
		}
	}

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

	public String getPriceTypeName() {
		return priceTypeName.toString();
	}

	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = PriceType.valueOf(priceTypeName);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
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
		return "ProductPrice [id=" + id + "productId=" + productId + ", priceTypeName=" + priceTypeName + ", price="
			+ price + ", discountRate=" + discountRate + ", createDate=" + createDate + ", modifyDate=" + modifyDate
			+ "]";
	}

}