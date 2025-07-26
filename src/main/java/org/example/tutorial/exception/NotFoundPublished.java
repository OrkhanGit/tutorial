package org.example.tutorial.exception;

public class NotFoundPublished extends RuntimeException {
    public NotFoundPublished(String message) {
        super(message);
    }
}
