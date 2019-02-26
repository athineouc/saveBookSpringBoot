package hello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(String exception) {
        super(exception);
    }
}
