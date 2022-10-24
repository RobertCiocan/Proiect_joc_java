package ExceptionHandle;

import java.io.IOException;

public class AllExceptions extends IOException {

    public AllExceptions(String message) {
        super(message);
    }

    public AllExceptions(String message, Throwable cause){
        super(message,cause);
    }
}