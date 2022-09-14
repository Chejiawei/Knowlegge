package com.chejw.top.Util.Exception;

public class CHEJWRuntimeException extends RuntimeException {

    public CHEJWRuntimeException(Throwable Message){
        super(Message);
    }

    public CHEJWRuntimeException(String Message){
        super(Message);
    }
}
