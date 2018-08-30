package com.nts.connect.pjt3.dto.result;

public class ResultJson<T> {
	private String result;
	private T body;

	private ResultJson(Builder<T> builder) {
		this.result = builder.result;
		this.body = builder.body;
	}

	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

	public String getResult() {
		return result;
	}

	public T getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "ResultJson [result=" + result + ", message=" + body + "]";
	}

	public static class Builder<S> {
		private String result;
		private S body;

		public Builder<S> setResult(String result) {
			this.result = result;
			return this;
		}

		public Builder<S> setBody(S body) {
			this.body = body;
			return this;
		}

		public ResultJson<?> build() {
			return new ResultJson<>(this);
		}
	}
}