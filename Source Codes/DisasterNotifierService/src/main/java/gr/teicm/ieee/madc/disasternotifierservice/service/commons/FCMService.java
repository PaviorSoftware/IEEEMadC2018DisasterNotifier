package gr.teicm.ieee.madc.disasternotifierservice.service.commons;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface FCMService {

    void sentToOne(JSONObject data, String token) throws JSONException;

    void sentToMultiple(JSONObject data, List<String> tokens) throws JSONException;

}
