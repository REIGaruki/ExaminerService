package pro.sky.examiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RepositoryIsEmptyException extends RuntimeException{
    public RepositoryIsEmptyException(String message) {
        super(message);
    }
}
