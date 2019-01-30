package com.example.address.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

import com.example.address.deserialization.FeedDeserializer;
import com.example.address.deserialization.LongPropertyDeserializer;
import com.example.address.deserialization.StringPropertyDeserializer;
import com.example.address.model.Feed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class FullResponseBuilder {
    public static String getFullResponse(final HttpURLConnection con) throws IOException {
        final StringBuilder fullResponseBuilder = new StringBuilder();

        fullResponseBuilder.append(con.getResponseCode()).append(" ").append(con.getResponseMessage()).append("\n");

        con.getHeaderFields().entrySet().stream().filter(entry -> entry.getKey() != null).forEach(entry -> {
            fullResponseBuilder.append(entry.getKey()).append(": ");
            final List<String> headerValues = entry.getValue();
            final Iterator<String> it = headerValues.iterator();
            if (it.hasNext()) {
                fullResponseBuilder.append(it.next());
                while (it.hasNext()) {
                    fullResponseBuilder.append(", ").append(it.next());
                }
            }
            fullResponseBuilder.append("\n");
        });

        Reader streamReader = null;

        if (con.getResponseCode() > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        final BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        final StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        fullResponseBuilder.append("Response: ").append(content.toString());

        return fullResponseBuilder.toString();
    }

    public static <T> T getFullResponseAsJson(final HttpURLConnection con, final Class<T> clazz) throws IOException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Feed.class, new FeedDeserializer());
        gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyDeserializer());
        gsonBuilder.registerTypeAdapter(LongProperty.class, new LongPropertyDeserializer());

        final Gson gson = gsonBuilder.create();

        Reader streamReader = null;
        System.out.println("ResponseCode: " + con.getResponseCode());
        System.out.println("ResponseMessage: " + con.getResponseMessage());
        if (con.getResponseCode() > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        final BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        final StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        try {
            return gson.fromJson(content.toString(), clazz);
        } catch (final JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
