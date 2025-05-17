package com.Leo.GFI_Desafio_Back.dto;

public class GenericResponseDto<T> {
    private String message;
    private T responseBody;

    public GenericResponseDto(String message, T responseBody) {
        this.message = message;
        this.responseBody = responseBody;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }
}
