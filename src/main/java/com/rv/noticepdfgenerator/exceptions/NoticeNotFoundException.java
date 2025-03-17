package com.rv.noticepdfgenerator.exceptions;

public class NoticeNotFoundException extends RuntimeException {

    public NoticeNotFoundException() {
        super("Notice not found !!");
    }

    public NoticeNotFoundException(String message) {
        super(message);
    }

}
