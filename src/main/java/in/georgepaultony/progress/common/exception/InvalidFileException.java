package in.georgepaultony.progress.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidFileException extends BaseException {

    public InvalidFileException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}