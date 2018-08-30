package com.nts.connect.pjt3.dto;

/**
 * @author	유재찬
 * @date	2018. 8. 13.
 */
public class ProductThumbnail {
	private int id;
	private int displayInfoId;
	private String description;
	private String saveFileName;
	private String placeName;
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDisplayInfoId() {
		return displayInfoId;
	}

	public void setDisplayInfoId(int displayInfoId) {
		this.displayInfoId = displayInfoId;
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

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ProductThumbnail [id=" + id + ", displatInfoId=" + displayInfoId + ", description=" + description
			+ ", saveFileName=" + saveFileName + ", placeName=" + placeName + ", content=" + content + "]";
	}

}
