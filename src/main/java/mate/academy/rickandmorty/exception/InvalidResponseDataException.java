package mate.academy.rickandmorty.exception;

public class InvalidResponseDataException extends RuntimeException {
    public InvalidResponseDataException(String message) {
        super(message);
    }
}
