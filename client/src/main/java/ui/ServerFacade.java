package ui;

import com.google.gson.Gson;
import model.UserData;
import org.eclipse.jetty.util.IO;
import request.LoginRequest;
import results.*;
//import requests.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static java.nio.file.Files.readString;

public class ServerFacade {

    private final String url;

    public ServerFacade(String url) {
        this.url = url;
    }

    public RegisterResults register(UserData request) throws ResponseException {
        var path = "/user";
        return this.carryRequest("POST", path, request, RegisterResults.class);
    }
    public UserData login(LoginRequest request) throws ResponseException {
        var path = "/session";
        return this.carryRequest("POST", path, request, UserData.class);
    }

    public LogoutAndJoinResults logout(LoginRequest request) throws ResponseException {
        var path = "/session";
        return this.carryRequest("DELETE", path, request, LogoutAndJoinResults.class);
    }


    private <T> T carryRequest(String endpoint, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            var json = new Gson();
//            System.out.println(this.url);
            URL url = new URI(this.url + path).toURL();
//            System.out.println("2");    // FIXME: Delete me
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            //post, get, delete, etc.
            http.setRequestMethod(endpoint);

            if (!Objects.equals(endpoint, "GET")) {
                http.setDoOutput(true);
                write(http, request);
            }

            http.connect();

            if (http.getResponseCode() != 200) {
                System.out.println("http response code was not correct\n");
                throw new ResponseException("Response code: " + http.getResponseCode() + ", message: " + http.getResponseMessage());
            }

            return readString(http, responseClass);
        }
        catch (Exception e) {
            throw new ResponseException("request was not carried -> " + e.getMessage());
        }
    }

    private static void write(HttpURLConnection http, Object request) throws IOException {
        if(request != null){
            http.addRequestProperty("Type", "application/json");
            var json = new Gson();
            String data = json.toJson(request);
            OutputStream reqBody = http.getOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(reqBody, StandardCharsets.UTF_8)){
                writer.write(data);
                writer.flush();
            }

        }
    }

    private static <T> T readString(HttpURLConnection http, Class<T> responseClass) throws IOException {
        //response
        T response = null;
        var json = new Gson();
        if(http.getContentLength() > 0){
            try (InputStream body = http.getInputStream()){
                InputStreamReader read = new InputStreamReader(body);
                if(responseClass != null){
                    response = json.fromJson(read, responseClass);
                }
            }
            catch (Exception e){
                throw new IOException("input stream failed -> " + e.getMessage());
            }
        }
        return response;
    }
}
