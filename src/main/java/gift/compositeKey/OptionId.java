package gift.compositeKey;

public class OptionId {
    int id;
    String option;

    public int getId() {
        return id;
    }

    public String getOption() {
        return option;
    }

    public OptionId(int id, String option) {
        this.id = id;
        this.option = option;
    }

    public OptionId() {
    }
}
