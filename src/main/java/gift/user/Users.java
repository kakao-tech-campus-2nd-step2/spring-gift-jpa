package gift.user;

import jakarta.persistence.*;

@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable=false, unique = true)
    private String email;
    @Column(nullable=false)
    private String password;

    public Users(){}

    public Users(String email,String password){
        this.email=email;
        this.password=password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}
