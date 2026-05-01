package com.fernanda.frontend.singleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class NetworkManager {
    private static NetworkManager instance;

    // Ganti dengan URL Spring Boot Anda. Anggap saja ini localhost untuk sekarang.
    private final String BASE_URL = "http://localhost:8080/api";

    private NetworkManager() {}

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public interface NetworkCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public void login(String username, String password, final NetworkCallback callback) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

        String jsonPayload = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        Net.HttpRequest httpRequest = requestBuilder.newRequest()
            .method(Net.HttpMethods.POST)
            .url(BASE_URL + "/auth/login")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .content(jsonPayload)
            .build();

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final String result = httpResponse.getResultAsString();
                final int statusCode = httpResponse.getStatus().getStatusCode();

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (statusCode >= 200 && statusCode < 300) {
                            callback.onSuccess(result);
                        } else {
                            callback.onFailure("Gagal login! Status Code: " + statusCode);
                        }
                    }
                });
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure("Koneksi mati/Server down: " + t.getMessage());
                    }
                });
            }

            @Override
            public void cancelled() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure("Permintaan dibatalkan.");
                    }
                });
            }
        });
    }
}
