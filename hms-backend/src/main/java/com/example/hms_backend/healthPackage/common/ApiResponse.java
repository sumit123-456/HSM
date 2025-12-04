package com.example.hms_backend.healthPackage.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;
    private List<T> dataList;

    public ApiResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Boolean success, String message, List<T> dataList) {
        this.success = success;
        this.message = message;
        this.dataList = dataList;
    }

    public ApiResponse(Boolean success, String message, T data, List<T> dataList) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.dataList = dataList;
    }


}
