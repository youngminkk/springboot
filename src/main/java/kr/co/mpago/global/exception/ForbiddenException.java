package kr.co.mpago.global.exception;

public class ForbiddenException extends BusinessException{
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
    public ForbiddenException(){
        super(ErrorCode.BAD_REQUEST);
    }
}