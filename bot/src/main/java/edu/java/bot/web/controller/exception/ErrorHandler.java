package edu.java.bot.web.controller.exception;

import edu.java.ApiErrorResponse;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorResponse> handleInvalidParameters(MethodArgumentNotValidException ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setDescription("Некорректные параметры запроса");
        errorResponse.setCode(ex.getStatusCode().toString());
        String errorId = UUID.randomUUID().toString();
        errorResponse.setErrorId(errorId);

        LOGGER.error("Error ID: {}", errorId, ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
