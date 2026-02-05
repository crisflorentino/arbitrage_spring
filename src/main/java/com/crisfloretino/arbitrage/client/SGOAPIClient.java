package com.crisfloretino.arbitrage.client;

import com.crisfloretino.arbitrage.model.APIResponse;
import com.crisfloretino.arbitrage.model.Event;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SGOAPIClient {
    private final OkHttpClient client;
    private final Gson gson;

    public SGOAPIClient(OkHttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    public Optional<APIResponse> fetchOdds(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Api-Key", "")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                return Optional.empty();
            }

            String json = response.body().string();
            return Optional.ofNullable(gson.fromJson(json, APIResponse.class));

        } catch (IOException e) {
            System.err.println("There was a problem when parsing the JSON, returning empty response.");
            return Optional.empty();
        }
    }

    public List<Event> collectEvents(APIResponse response) {
        return new ArrayList<>(response.data());
    }




}
