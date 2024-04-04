package edu.java.internal.controllers.exception;

import edu.java.ApiErrorResponse;
import edu.java.my.exception.ChatNotFoundException;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class ErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);
    private final static String ERROR_MESSAGE = "Error ID: {}";

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleInvalidParameters(MethodArgumentNotValidException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDescription("Некорректные параметры запроса");
        apiErrorResponse.setCode(ex.getStatusCode().toString());
        String errorId = UUID.randomUUID().toString();
        apiErrorResponse.setErrorId(errorId);

        LOGGER.error(ERROR_MESSAGE, errorId, ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        return httpStatusNotFound(ex);
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleNoSuchElementException(ChatNotFoundException ex) {
        return httpStatusNotFound(ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDescription("Некорректный тип параметра");
        apiErrorResponse.setCode(HttpStatus.BAD_REQUEST.toString());
        String errorId = UUID.randomUUID().toString();
        apiErrorResponse.setErrorId(errorId);

        LOGGER.error(ERROR_MESSAGE, errorId, ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiErrorResponse> httpStatusNotFound(Exception ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setDescription(ex.getMessage());
        apiErrorResponse.setCode(HttpStatus.NOT_FOUND.toString());
        String errorId = UUID.randomUUID().toString();
        apiErrorResponse.setErrorId(errorId);

        LOGGER.error(ERROR_MESSAGE, errorId, ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleAllOtherErrors(HttpServerErrorException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setCode(String.valueOf(ex.getStatusCode().value()));
        String errorId = UUID.randomUUID().toString();
        apiErrorResponse.setErrorId(errorId);

        LOGGER.error(ERROR_MESSAGE, errorId, ex);

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
