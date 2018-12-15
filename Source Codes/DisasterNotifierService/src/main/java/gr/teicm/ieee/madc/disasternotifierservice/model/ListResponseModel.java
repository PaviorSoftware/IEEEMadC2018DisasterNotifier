package gr.teicm.ieee.madc.disasternotifierservice.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ListResponseModel<T> extends ResponseModel {

    private final List<T> data;

    public ListResponseModel(HttpStatus httpStatus, List<T> data) {
        super(httpStatus);
        this.data = data;
    }

    public ListResponseModel(String message, List<T> data) {
        super(message);
        this.data = data;
    }

    public ListResponseModel(HttpStatus httpStatus, String message, List<T> data) {
        super(httpStatus, message);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }
}
