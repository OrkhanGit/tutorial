package org.example.tutorial.exception;

public class NotFoundList extends RuntimeException {
    public NotFoundList(String message) {
        super(message);
    }
}
