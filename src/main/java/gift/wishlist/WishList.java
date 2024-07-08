package gift.wishlist;

public class WishList {

    private String email;
    private String name;
    private int num;

    public WishList(){}

    public WishList(String email, String name, int num) {
        this.email=email;
        this.name=name;
        this.num=num;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num=num;
    }
}
