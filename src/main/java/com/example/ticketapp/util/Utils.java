package com.example.ticketapp.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {

    public static Map<String, String> messageToMap(String message) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("error", message);

        return body;
    }
}
