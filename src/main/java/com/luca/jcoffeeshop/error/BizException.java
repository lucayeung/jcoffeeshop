package com.luca.jcoffeeshop.error;

public class BizException extends RuntimeException {
    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BizException(Throwable throwable) {
        super(throwable);
    }
}
