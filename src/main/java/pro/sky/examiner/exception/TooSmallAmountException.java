package pro.sky.examiner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TooSmallAmountException extends RuntimeException{
    public TooSmallAmountException(String message) {
        super(message);
    }
}
