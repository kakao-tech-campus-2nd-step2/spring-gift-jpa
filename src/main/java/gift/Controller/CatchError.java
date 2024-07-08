package gift.Controller;

public class CatchError {
    public static boolean isCorrectName(String name){
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

    public static boolean isContainsKakao(String name){
        return name.contains("카카오");
    }
}
