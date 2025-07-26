package org.example.tutorial.exception;

public class NotFoundTitle extends RuntimeException {
    public NotFoundTitle(String message) {
        super(message);
    }
}
