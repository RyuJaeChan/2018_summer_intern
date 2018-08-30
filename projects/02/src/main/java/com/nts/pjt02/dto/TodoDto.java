package com.nts.pjt02.dto;

/**
 * todo 데이터 타입을 정의하는 클래스
 * 
 * @author 유재찬
 *
 */

public class TodoDto {
	private long id;
	private String name;
	private String regdate;
	private int sequence;
	private String title;
	private String type;

	public TodoDto() {

	}

	public TodoDto(String title, String name, int sequence, String type) {
		this.title = title;
		this.name = name;
		this.sequence = sequence;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TodoDto [id=" + id + ", name=" + name + ", regdate=" + regdate + ", sequence=" + sequence + ", title="
			+ title + ", type=" + type + "]";
	}
}
