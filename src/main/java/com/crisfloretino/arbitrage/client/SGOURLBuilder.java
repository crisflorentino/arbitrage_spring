package com.crisfloretino.arbitrage.client;

import okhttp3.HttpUrl;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SGOURLBuilder {
    private static HttpUrl makeBase() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api.sportsgameodds.com")
                .addPathSegment("v2")
                .addPathSegment("events")
                .build();
    }

    public static HttpUrl buildURL(Map<String, Object> queryParamters) {
        HttpUrl baseURL = makeBase();
        HttpUrl.Builder urlBuilder = baseURL.newBuilder();

        for (Map.Entry<String, Object> entry : queryParamters.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }

        return urlBuilder.build();
    }
}
