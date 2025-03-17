package com.rv.noticepdfgenerator.exceptions;

public class PdfGenerationException extends RuntimeException {

    public PdfGenerationException() {
        super("Failed to generate pdf !!");
    }

    public PdfGenerationException(String message) {
        super(message);
    }

    public PdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
