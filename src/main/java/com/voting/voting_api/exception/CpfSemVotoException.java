package com.voting.voting_api.exception;

public class CpfSemVotoException extends RuntimeException {
    public CpfSemVotoException(String message) {
        super(message);
    }
}