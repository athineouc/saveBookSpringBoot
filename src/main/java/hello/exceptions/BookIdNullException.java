package hello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookIdNullException extends RuntimeException {
    public BookIdNullException(String exception) {
        super(exception);
    }

}
