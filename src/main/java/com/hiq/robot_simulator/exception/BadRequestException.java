package com.hiq.robot_simulator.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException (String errMsg) {
        super(errMsg);
    }
}
