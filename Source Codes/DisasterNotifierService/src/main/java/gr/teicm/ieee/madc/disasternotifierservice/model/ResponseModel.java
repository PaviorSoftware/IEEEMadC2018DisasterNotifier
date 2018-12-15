package gr.teicm.ieee.madc.disasternotifierservice.model;

import org.springframework.http.HttpStatus;

public class ResponseModel {
    private HttpStatus httpStatus;
    private String message;

    ResponseModel(HttpStatus httpStatus) {
        this(httpStatus, "");
    }

    ResponseModel(String message) {
        this(HttpStatus.OK, message);
    }

    ResponseModel(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
