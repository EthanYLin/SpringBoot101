package cn.edu.fudan.se.springboot101demo.exception;

public class NotFoundException extends InvalidRequestException {
    public NotFoundException(String message) {
        super(message);
    }
}
