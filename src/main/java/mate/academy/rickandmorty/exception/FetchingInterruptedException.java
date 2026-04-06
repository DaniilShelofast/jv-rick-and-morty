package mate.academy.rickandmorty.exception;

public class FetchingInterruptedException extends RuntimeException {
    public FetchingInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }
}
