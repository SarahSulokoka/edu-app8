package gr.aueb.cf.eduapp.core;

import gr.aueb.cf.eduapp.core.exceptions.*;
import gr.aueb.cf.eduapp.dto.ResponseMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.Binding;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {

    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException e) {
        log.error("Validation failed. Message={}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldError()) {
            errors.put(filedError.getField(), fieldError.getDefaultMessage());

        } return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppObjectNotFoundException.class)
    public ResponseEntity<ResponseMessageDTO> handlerConstrainViolationException(AppObjectNotFoundException e) {
        log.warn("Entity not found. Message={}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessageDTO(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(AppObjectInvalidArgumentException.class)
    public ResponseEntity<ResponseMessageDTO> handlerConstrainViolationException(AppObjectInvalidArgumentException e) {
        log.warn("Invalid Argument. Message={}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(AppObjectAlreadyExists.class)
    public ResponseEntity<ResponseMessageDTO> handlerConstrainViolationException(AppObjectAlreadyExists e) {
        log.warn("Entity already exists. Message={}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessageDTO(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(AppObjectNotAuthorizedException.class)
    public ResponseEntity<ResponseMessageDTO> handlerConstrainViolationException(AppObjectNotAuthorizedException e) {
        log.warn("Authorization failed. Message={}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessageDTO(e.getCode(), e.getMessage()));
    }


}
