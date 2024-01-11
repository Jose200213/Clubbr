package com.Clubbr.Clubbr.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    private String fromResourceName;
    private String fromFieldName;
    private Object fromFieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no se encontró con: %s = '%s'", resourceName, fieldName, fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String resourceName) {
        super(String.format("No hay registros de %s en el sistema.", resourceName));

        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue, String fromResourceName, String fromFieldName, Object fromFieldValue) {
        super(String.format("No hay registros de %s con %s = '%s' en el %s con %s = '%s'.", resourceName, fieldName, fieldValue, fromResourceName, fromFieldName, fromFieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.fromResourceName = fromResourceName;
        this.fromFieldName = fromFieldName;
        this.fromFieldValue = fromFieldValue;
    }
}
