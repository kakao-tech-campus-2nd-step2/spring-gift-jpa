package gift.controller;

public class CatchError {
    public boolean isCorrectName(String name){
        if(name.length()>15){
            return false;
        }
        String letters = "()[]+-&/_ ";
        for(int i=0; i<name.length(); i++){
            char one = name.charAt(i);
            if(!Character.isLetterOrDigit(one) && !letters.contains(Character.toString(one))){
                return false;
            }
        }
        return true;
    }

    public boolean isContainsKakao(String name){
        return name.contains("카카오");
    }
}
