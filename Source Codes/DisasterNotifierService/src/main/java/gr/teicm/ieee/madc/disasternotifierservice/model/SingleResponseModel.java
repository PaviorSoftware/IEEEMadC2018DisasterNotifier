package gr.teicm.ieee.madc.disasternotifierservice.model;

import org.springframework.http.HttpStatus;

public class SingleResponseModel<T> extends ResponseModel {

    private final T data;

    public SingleResponseModel(HttpStatus httpStatus, T data) {
        super(httpStatus);
        this.data = data;
    }

    public SingleResponseModel(String message, T data) {
        super(message);
        this.data = data;
    }

    public SingleResponseModel(HttpStatus httpStatus, String message, T data) {
        super(httpStatus, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
