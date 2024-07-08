package gift.exception.BadRequestExceptions;

public class NoSuchProductIdException extends BadRequestException {
    public NoSuchProductIdException(){
        super();
    }
    public NoSuchProductIdException(String message){
        super(message);
    }

}
