package gift.entity;


import jakarta.persistence.*;

@Entity
@Table(name="wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private long productId;


    private String email;
    private String type;


    public Wish(){}

    public Wish(String email, String type, long productId) {
        this.email = email;
        this.type = type;
        this.productId = productId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


}

