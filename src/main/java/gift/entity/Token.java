package gift.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    private Long id;
    @NonNull
    private String tokenValue;

    public Token() {
    }

    public Token(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Token(Long newId, String tokenValue) {
        this.id = newId;
        this.tokenValue= tokenValue;
    }

    public Long getId() {
        return id;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
