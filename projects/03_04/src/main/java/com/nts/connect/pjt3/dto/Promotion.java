package com.nts.connect.pjt3.dto;

/**
 * @author	유재찬
 * @date	2018. 8. 6.
 */
public class Promotion {
	private int id;
	private int productId;
	private String description;
	private String saveFileName;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	@Override
	public String toString() {
		return "Promotion [id=" + id + ", productId=" + productId + ", description=" + description + ", saveFileName="
			+ saveFileName + "]";
	}

}
