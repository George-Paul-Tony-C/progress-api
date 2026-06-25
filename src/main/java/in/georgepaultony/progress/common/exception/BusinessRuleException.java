package in.georgepaultony.progress.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessRuleException extends BaseException {
    public BusinessRuleException(String message) {
        super(message , HttpStatus.BAD_REQUEST);
    }
}
