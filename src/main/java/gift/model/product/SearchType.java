package gift.model.product;

public enum SearchType {
    NAME("이름"),
    PRICE("가격"),
    ALL("전체");

    private String description;

    SearchType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static SearchType fromDescription(String description) {
        for (SearchType type : SearchType.values()) {
            if (type.getDescription().equals(description)) {
                return SearchType.valueOf(type.name());
            }
        }
        throw new IllegalArgumentException("Unknown description: " + description);
    }
}
