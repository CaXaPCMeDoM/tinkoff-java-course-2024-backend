package edu.java.bot.web.controller.exception;

import edu.java.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorResponse> handleInvalidParameters(MethodArgumentNotValidException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription("Некорректные параметры запроса");
        errorResponse.setCode(ex.getStatusCode().toString());
        errorResponse.setExceptionName(ex.getClass().getName());
        errorResponse.setExceptionMessage(ex.getMessage());
        // + нужно добавить errorResponse.setStacktrace(); как-то через логгер только нужные поля

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
