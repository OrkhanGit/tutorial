package org.example.tutorial.exception;

public class NotFoundId extends RuntimeException {

    public NotFoundId(String msg) {
        super(msg);
    }

}
