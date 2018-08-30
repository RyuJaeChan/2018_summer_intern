package com.nts.connect.pjt3.dto.result;

import java.util.List;

/**
 * json 전송을 위한 클래스
 * 리스트와 리스트의 크기를 가지고 있다. 
 * 
 * @author	유재찬
 * @param <T>
 * @date	2018. 8. 7.
 */
public class ItemList<T> {
	private List<T> items;
	private long size;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ItemList [item=" + items + ", size=" + size + "]";
	}

}
