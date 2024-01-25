package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.response.LoginCallbackResponse;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpCallService {

    public String call(String method, String reqURL, String header, String param) {
        StringBuilder result = new StringBuilder();

        try {
            String response = "";
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", header);

            if (param != null) {
                conn.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                bw.write(param);
                bw.flush();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String callWithToken(String method, String reqURL, String accessToken) {
        return callWithToken(method, reqURL, accessToken, null);
    }

    public String callWithToken(String method, String reqURL, String accessToken, String param) {
        String header = "Bearer " + accessToken;
        return call(method, reqURL, header, param);
    }
}
