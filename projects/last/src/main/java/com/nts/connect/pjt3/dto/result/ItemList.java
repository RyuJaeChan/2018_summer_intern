package com.nts.connect.pjt3.dto.result;

import java.util.List;

public class ItemList<T> {
	private int size;
	private List<?> items;

	private ItemList(Builder<T> builder) {
		this.items = builder.items;
		this.size = builder.size;
	}

	public static Builder<?> builder() {
		return new Builder<>();
	}

	public List<?> getItems() {
		return items;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "ItemList [ size=" + size + ", items=" + items + "]";
	}

	public static class Builder<S> {
		private List<?> items;
		private int size;

		public Builder<S> setItems(List<?> items) {
			this.items = items;
			return this;
		}

		public Builder<S> setSize(int size) {
			this.size = size;
			return this;
		}

		public ItemList<S> build() {
			return new ItemList<>(this);
		}
	}
}