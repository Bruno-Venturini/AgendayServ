package com.agenday.agendayserv.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends RuntimeException {
    public BusinessException(String name) {
        super(name);
    }

    public BusinessException(String name, Throwable cause) {
        super(name, cause);
    }
}
