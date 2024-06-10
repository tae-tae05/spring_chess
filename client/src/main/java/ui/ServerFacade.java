package ui;

import com.google.gson.Gson;
import model.UserData;
import org.eclipse.jetty.util.IO;
import request.LoginRequest;
import results.*;
//import requests.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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


    private <T> T carryRequest(String endpoint, String method, Object request, Class<T> responseClass) throws ResponseException {
        try {
            var json = new Gson();
            URL url = new URI(this.url + endpoint).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            //post, get, delete, etc.
            http.setRequestMethod(method);

            http.connect();

            if (http.getResponseCode() != 200) {
                System.out.println("http response code was not corrent\n");
                throw new ResponseException("Response code: " + http.getResponseCode() + ", message: " + http.getResponseMessage());
            }

            return readString(http, responseClass);
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }

    private static <T> T readString(HttpURLConnection http, Class<T> responseClass) throws IOException {
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
                throw new IOException(e.getMessage());
            }
        }
        return response;
    }
}
