package com.usersapp.error;

import com.usersapp.error.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorService errorService;
    private final String applicationName;

    public GlobalExceptionHandler(ErrorService errorService,
                                  @Value("${spring.application.name}") String applicationName) {
        this.errorService = errorService;
        this.applicationName = applicationName;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO resourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                              HttpServletRequest webRequest) {

        return this.errorService.getErrorDTO()
                .stamp(new Date())
                .message(resourceNotFoundException.getMessage())
                .applicationName(this.applicationName);
    }
}
