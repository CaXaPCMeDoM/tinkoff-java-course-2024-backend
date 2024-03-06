package edu.java.internal.controllers.exception;

import edu.java.ApiErrorResponse;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleInvalidParameters(MethodArgumentNotValidException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDescription("Некорректные параметры запроса");
        apiErrorResponse.setCode(ex.getStatusCode().toString());
        apiErrorResponse.setExceptionName(ex.getClass().getName());
        apiErrorResponse.setExceptionMessage(ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDescription(ex.getMessage());
        apiErrorResponse.setCode(HttpStatus.NOT_FOUND.toString());
        apiErrorResponse.setExceptionName(ex.getClass().getName());
        apiErrorResponse.setExceptionMessage(ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDescription("Некорректный тип параметра");
        apiErrorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        apiErrorResponse.setExceptionName(ex.getClass().getName());
        apiErrorResponse.setExceptionMessage(ex.getMessage());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
