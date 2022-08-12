package com.example.batchemailsender;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class StartApp {

    static ByteArrayOutputStream responseBodyBaos = null;
    static Scanner httpResponseBodyScanner = null;

    public static void main(String[] args) {

        try {
            URL robotsUrl = new URL("https://76n52oikt627opw3lvchqtemj40xtoqz.lambda-url.eu-west-2.on.aws/");
            HttpURLConnection urlConnection = (HttpURLConnection) robotsUrl.openConnection();

            httpResponseBodyScanner = new Scanner(urlConnection.getInputStream());

            // Use a ByteArrayOutputStream to store the contents of the HTTP response body
            responseBodyBaos = new ByteArrayOutputStream();
            while(httpResponseBodyScanner.hasNextLine()) {
                responseBodyBaos.write(httpResponseBodyScanner.nextLine().getBytes());
            }
            responseBodyBaos.close();
            httpResponseBodyScanner.close();

            // Verify license key
            String robotsContent = responseBodyBaos.toString();
            if (robotsContent.trim().equals("12345")) {
                MainController.main(args);
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();

        } finally {
            if (responseBodyBaos != null) {
                try {
                    responseBodyBaos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            if (httpResponseBodyScanner != null) {
                httpResponseBodyScanner.close();
            }
        }
    }

}
