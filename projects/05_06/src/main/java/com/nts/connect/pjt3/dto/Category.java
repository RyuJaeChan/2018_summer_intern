package com.nts.connect.pjt3.dto;

/**
 * @author	유재찬
 * @date	2018. 8. 6.
 */
public class Category {
	private int id;
	private String name;
	private long count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", count=" + count + "]";
	}

}
