package com.minhdunk.research.exception;

public class TestTypeExistsForDocumentException extends RuntimeException {
    public TestTypeExistsForDocumentException(String message) {
        super(message);
    }

    public TestTypeExistsForDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
