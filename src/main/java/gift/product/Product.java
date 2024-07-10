package gift.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "product")
public class Product {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "email", nullable = false)
   private String email;

   @Column(name = "password", nullable = false)
   private String password;

   protected Product() {}

    public Product(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

