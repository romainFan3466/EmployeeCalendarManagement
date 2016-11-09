package com.main.exception;

/**
 * Created by romain on 04/10/16.
 */
public class InvalidFieldException extends Exception {

    private String fieldName;

    public InvalidFieldException(String fieldName, String message){
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName(){
        return this.fieldName;
    }
}
