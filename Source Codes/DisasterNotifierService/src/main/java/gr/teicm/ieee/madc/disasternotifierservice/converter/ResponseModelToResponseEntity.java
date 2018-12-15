package gr.teicm.ieee.madc.disasternotifierservice.converter;

import gr.teicm.ieee.madc.disasternotifierservice.model.ResponseModel;
import org.springframework.http.ResponseEntity;

public class ResponseModelToResponseEntity {

    public static ResponseEntity<?> convert(ResponseModel responseModel) {
        return ResponseEntity
                .status(responseModel.getHttpStatus())
                .body(responseModel);
    }

}
