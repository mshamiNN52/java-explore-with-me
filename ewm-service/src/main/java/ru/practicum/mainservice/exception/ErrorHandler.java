package ru.practicum.mainservice.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.mainservice.service.UtilityClass.formatTimeToString;

@RestControllerAdvice(basePackages = "ru.practicum.mainservice")
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEntityNotFoundException(final EntityNotFoundException e) {
        return createErrorMessage(e, HttpStatus.NOT_FOUND, "The required object was not found.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final MethodArgumentNotValidException e) {
        List<String> fieldErrorsMessage = e.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return Map.of("status", "BAD_REQUEST",
                "reason", "Incorrectly made request.",
                "message", fieldErrorsMessage.toString(),
                "timestamp", formatTimeToString(LocalDateTime.now())
        );
    }

    @ExceptionHandler({TypeMismatchException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(final Exception e) {
        return createErrorMessage(e, HttpStatus.BAD_REQUEST, "Incorrectly made request.");
    }

    @ExceptionHandler({DataException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictException(final Exception e) {
        String reason = e instanceof DataException ? e.getMessage() : "The object already exists.";
        return createErrorMessage(e, HttpStatus.CONFLICT, reason);
    }

    private Map<String, String> createErrorMessage(Exception e, HttpStatus status, String reason) {
        Optional<String> messageOptional = Optional.ofNullable(e.getMessage());
        String message = messageOptional.orElse("-");
        return Map.of("status", status.toString(),
                "reason", reason,
                "message", message,
                "timestamp", formatTimeToString(LocalDateTime.now())
        );
    }

}