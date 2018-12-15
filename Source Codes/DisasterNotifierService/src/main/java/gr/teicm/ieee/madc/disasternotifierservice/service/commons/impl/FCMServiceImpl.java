package gr.teicm.ieee.madc.disasternotifierservice.service.commons.impl;

import gr.teicm.ieee.madc.disasternotifierservice.config.FirebaseUtil;
import gr.teicm.ieee.madc.disasternotifierservice.model.FirebaseResponse;
import gr.teicm.ieee.madc.disasternotifierservice.service.commons.FCMService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FCMServiceImpl implements FCMService {
    @Override
    public void sentToOne(JSONObject data, String token) throws JSONException {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(
                new HeaderRequestInterceptor(
                        "Authorization",
                        "key=" + FirebaseUtil.FIREBASE_SERVER_KEY
                )
        );
        interceptors.add(
                new HeaderRequestInterceptor(
                        "Content-Type",
                        "application/json")
        );
        restTemplate.setInterceptors(interceptors);

        data.put("to", token);

        FirebaseResponse firebaseResponse = restTemplate.postForObject(
                FirebaseUtil.FIREBASE_API_URL,
                data.toString(),
                FirebaseResponse.class
        );

    }

    @Override
    public void sentToMultiple(JSONObject data, List<String> tokens) throws JSONException {
        for (String token : tokens) {
            sentToOne(data, token);
        }
    }
}
