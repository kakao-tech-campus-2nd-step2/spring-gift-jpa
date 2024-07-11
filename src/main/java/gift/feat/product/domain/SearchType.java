package gift.feat.product.domain;



public enum SearchType {
	NAME("이름");

	private final String description;

	SearchType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}