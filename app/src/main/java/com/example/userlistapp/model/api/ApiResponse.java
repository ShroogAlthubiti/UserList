package com.example.userlistapp.model.api;

public class ApiResponse<T> {
    private T data;
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
