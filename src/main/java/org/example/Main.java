package org.example;

import java.util.Scanner;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Main {

    final static int STARTPARSE = 5;
    final static int ENDPARSE = 13;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        OkHttpClient client = new OkHttpClient();
        String requestURL = "https://v6.exchangerate-api.com/v6/";
        final String param = "/latest/";


        System.out.print("Enter your 'ExchangeRate-API' key: ");
        String key = scan.nextLine();

        System.out.print("Enter the 'base' currency code: ");
        String base = scan.nextLine();

        System.out.print("Amount: ");
        double val = scan.nextDouble();
        scan.nextLine();

        System.out.print("Enter the 'convert' currency code: ");
        String convert = scan.nextLine();

        requestURL += key;
        requestURL += param;
        requestURL += base;

        Request request = new Request.Builder()
                .url(requestURL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String data = response.body().string();

            int index = data.indexOf(convert);

            double rate = Double.parseDouble(data.substring(index + STARTPARSE, index + ENDPARSE));
            double amount = val * rate;

            System.out.println();
            System.out.println("Info: ");
            System.out.println("Rate: 1 " + base + " is " + rate + " " + convert + ".");
            System.out.println(val + " " + base + " is equivalent to " + amount + " " + convert + ".");

        }
        catch (IOException ex) {
            System.out.println("Error executing request!" + ex.getLocalizedMessage());
            System.exit(1);
        }
    }
}