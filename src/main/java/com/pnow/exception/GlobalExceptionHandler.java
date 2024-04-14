package com.pnow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //JPA 엔티티 유효성 검사 오류 @NotNull, @Size, @Pattern 등
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolatedException(
            ConstraintViolationException ex
    ) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = constraintViolations.stream()
                .map(
                        constraintViolation ->
                                extractField(constraintViolation.getPropertyPath()) + ", " + constraintViolation.getMessage()
                )
                .collect(Collectors.toList());

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }
    //컨트롤러 @Valid 유효성 검사 오류, @RequestBody, @RequestParam, @PathVariable 등의 바인딩 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream()
                .map(
                        fieldError ->
                                "(" + fieldError.getField() + ") " + fieldError.getDefaultMessage()
                )
                .collect(Collectors.toList());

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }
    //데이터베이스 id 없을 때 오류
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundExceptionException(
            EntityNotFoundException ex
    ) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    //잘못된 입력값 들어왔을 때 오류
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    private String extractField(Path path) {
        String[] splittedArray = path.toString().split("[.]");
        int lastIndex = splittedArray.length - 1;
        return splittedArray[lastIndex];
    }
}
