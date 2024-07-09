package gift.user;

import jakarta.persistence.*;

@Table(name="users")
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public User(){}

    public User(String email,String password){
        this.email = email;
        this.password = password;
    }
    public long getId(){return id;}

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}
