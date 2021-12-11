package com.example.auto_sign;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;


public class HomeView {

    private String url;

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void sendURL(String url) throws JSONException {

        JSONObject json = new JSONObject();
        json.put("url", url);

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://python-microservice.herokuapp.com/download");
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
        }
    }

    public void downloadVideo(String url) throws IOException {
        DownloadObject.downloadObject("autosign-334513", "data_bucket_video_swag", "finals.mp4", "/Users/francescocenciarelli/Desktop/University/Year3/Programming3 /projc/Auto_Sign/src/main/resources/static/images/file.mp4");
    }
}
