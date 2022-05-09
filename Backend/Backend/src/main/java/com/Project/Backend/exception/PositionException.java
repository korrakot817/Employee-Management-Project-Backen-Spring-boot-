package com.Project.Backend.exception;

public class PositionException extends BaseException{
    public PositionException(String code) {
        super("position." + code);
    }

    //CreateException
    public static PositionException createPositionNull(){
        return new PositionException("create.position.null");
    }

    //GetException
    public static PositionException positionNull(){
        return new PositionException("null");
    }

}
