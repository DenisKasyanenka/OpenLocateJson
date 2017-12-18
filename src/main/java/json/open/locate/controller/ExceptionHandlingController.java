package json.open.locate.controller;

import json.open.locate.model.error.BasicError;
import json.open.locate.model.error.ErrorId;
import json.open.locate.model.error.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlingController {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    private static final String TRUNCATE_INVALID_JSON_MESSAGE = "\n at [Source";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleBindException(ConstraintViolationException exception) {
        BasicError error = constructAndLogValidationError(HttpStatus.BAD_REQUEST.toString(),
                exception.getConstraintViolations());
        return buildResponse(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity handleServerException(Throwable throwable) {
        LOGGER.error("Unknown error happened", throwable);
        BasicError error = constructAndLogError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ErrorId.GENERIC_ERROR,
                "Unknown error occurred");
        return buildResponse(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class,})
    public ResponseEntity handleServerException(HttpRequestMethodNotSupportedException ex) {
        BasicError error = constructAndLogError(HttpStatus.METHOD_NOT_ALLOWED.toString(), ErrorId.GENERIC_ERROR,
                ex.getMessage());
        return buildResponse(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class,})
    public ResponseEntity handleServerException(Exception ex) {
        BasicError error = constructAndLogError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(), ErrorId.GENERIC_ERROR,
                ex.getMessage());
        return buildResponse(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleInvalidJsonException(HttpMessageNotReadableException exception) {
        String message1 =
                exception.getRootCause() != null ? exception.getRootCause().getMessage() : exception.getMessage();
        String message = StringUtils.substringBefore(message1, TRUNCATE_INVALID_JSON_MESSAGE);
        BasicError error = constructAndLogError(HttpStatus.BAD_REQUEST.toString(), ErrorId.INVALID_PAYLOAD_ERROR, message);

        return buildResponse(error, HttpStatus.BAD_REQUEST);
    }

    private BasicError constructAndLogError(String status, ErrorId errorId, String detail) {
        BasicError error = new BasicError(UUID.randomUUID(), status, errorId, detail);
        LOGGER.error(error.toString());
        return error;
    }

    private BasicError constructAndLogValidationError(String status, Set<ConstraintViolation<?>> constraintViolations) {
        BasicError basicError = new BasicError(UUID.randomUUID(), status, ErrorId.VALIDATION_ERROR, "Validation Error");
        basicError.setValidationErrors(constraintViolations.stream().map(
                x-> new ValidationError(
                        x.getPropertyPath().toString(),
                        x.getMessage())).collect(Collectors.toList()));
        LOGGER.error(basicError.toString());
        return basicError;
    }

    private <T> ResponseEntity buildResponse(T error, HttpStatus status) {
        return new ResponseEntity(error, status);
    }
}
