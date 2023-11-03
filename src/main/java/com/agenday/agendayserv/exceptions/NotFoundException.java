package com.agenday.agendayserv.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private static final String MESSAGE = " não encontrado.";

    public NotFoundException(String name) {
        super(name + MESSAGE);
    }

    public NotFoundException(String name, Throwable cause) {
        super(name + MESSAGE, cause);
    }
}
