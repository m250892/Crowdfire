package com.manoj.crowfire.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by manoj on 29/06/16.
 */
public class NetworkUtils {

    public static InputStream httpGetCall(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return conn.getInputStream();
        /*HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        if (response == null || response.getStatusLine().getStatusCode() != Constants.HTTP_SUCCESS_CODE) {
            return null;
        }
        return response.getEntity().getContent();*/
    }

    public static String readDataFromInputStream(InputStream inputStream) {
        Reader reader;
        try {
            reader = new InputStreamReader(new BufferedInputStream(inputStream));
            BufferedReader br = new BufferedReader(reader);
            String line;
            String res = "";
            while ((line = br.readLine()) != null) {
                res += line;
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
