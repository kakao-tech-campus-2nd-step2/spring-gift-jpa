package gift.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class Option {
    @Id
    int id;
    @Id
    @Column(nullable = false)
    String option;

    public int getId() {
        return id;
    }

    public String getOption() {
        return option;
    }

    public Option(int id, String option) {
        this.id = id;
        this.option = option;
    }

    public Option() {
    }
}