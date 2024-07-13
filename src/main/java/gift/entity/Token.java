package gift.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "tokens")
public class Token {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        this.tokenValue = tokenValue;
    }

    public Long getId() {
        return id;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
