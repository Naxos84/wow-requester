package com.example.address.helper;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class UrlBuilder {
    public static String buildGetUrl(final String basicUrl, final Map<String, String> params)
            throws UnsupportedEncodingException {
        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(basicUrl);
        urlBuilder.append("?");
        urlBuilder.append(ParameterStringBuilder.getParamsString(params));
        return urlBuilder.toString();
    }

    // public static String buildPostUrl(final String basicUrl, final
    // Map<String, String> params)
    // throws UnsupportedEncodingException {
    // final StringBuilder urlBuilder = new StringBuilder();
    // urlBuilder.append(basicUrl);
    // urlBuilder.append("?");
    // urlBuilder.append(ParameterStringBuilder.getParamsString(params));
    // return urlBuilder.toString();
    // }
}
