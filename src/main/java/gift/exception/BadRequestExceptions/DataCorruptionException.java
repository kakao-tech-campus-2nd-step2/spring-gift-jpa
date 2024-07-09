package gift.exception.BadRequestExceptions;

public class DataCorruptionException extends BadRequestException{
    public DataCorruptionException() { super(); }
    public DataCorruptionException(String message) { super(message); }
}
