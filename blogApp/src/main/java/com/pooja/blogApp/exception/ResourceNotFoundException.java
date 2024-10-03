package com.pooja.blogApp.exception;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    String fieldName;
    long fieldValue;
    String fieldValue1;

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(resourceName+" not found with "+fieldName+ " : "+fieldValue);
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue1) {
        super(resourceName+" not found with "+fieldName+ " : "+fieldValue1);
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue1=fieldValue1;
    }
}
