package gift.dto;

import gift.entity.MemberEntity;

public class MemberRequest {
    String email;
    String password;

    public MemberRequest(){}

    public MemberRequest(String email, String password){
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MemberEntity toMemberEntity(){
        return new MemberEntity(this.email, this.getPassword());
    }
}
