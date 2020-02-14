package com.news.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class PostCreationFailedException extends RuntimeException {
    public PostCreationFailedException() {
        super();
    }

    public PostCreationFailedException(String message) {
        super(message);
    }

    public PostCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
