package dev.ctc.learning.rappellemoiapi.files;

import dev.ctc.learning.rappellemoiapi.base.BaseErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.FileAlreadyExistsException;
import java.util.Map;

@ControllerAdvice("dev.ctc.learning.rappellemoiapi")
public class FileErrorHandler extends BaseErrorHandler {

    @ExceptionHandler(FileAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleFileAlreadyExistsException(
            FileAlreadyExistsException ex
    ) {
        return new ResponseEntity<>(convert(ex), HttpStatus.CONFLICT);
    }

}
