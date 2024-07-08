package gift.admin;

public enum AdminPageConfigure {
    PAGE_SIZE(5),
    MAX_PAGE_INDEX(5);

    private final Integer value;

    AdminPageConfigure(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
