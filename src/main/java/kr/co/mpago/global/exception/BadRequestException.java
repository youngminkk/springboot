package kr.co.mpago.global.exception;

public class BadRequestException extends BusinessException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}