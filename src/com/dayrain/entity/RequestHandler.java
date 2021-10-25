package com.dayrain.entity;

import com.dayrain.utils.JackSonUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements HttpHandler {

    private final ServerUrl serverUrl;

    public RequestHandler(ServerUrl serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(RequestType.GET == serverUrl.getRequestType()) {
            handleGetRequest(exchange);
        }else if(RequestType.POST == serverUrl.getRequestType()) {
            handlePostRequest(exchange);
        }

        response(exchange, serverUrl.getResponseBody());
     }

    private void handleGetRequest(HttpExchange exchange) {
        String queryString = exchange.getRequestURI().getQuery();
        System.out.println(queryString);
    }

    private void handlePostRequest(HttpExchange exchange) {
        String requestBody = JackSonUtils.getFromInputStream(exchange.getRequestBody());
        System.out.println(requestBody);
    }

    private void response(HttpExchange exchange, String jsonBody) {
        setHeaders(exchange);
        try {
            exchange.sendResponseHeaders(200, jsonBody.length());
            exchange.setAttribute("Content-Type","application/json; charset=utf-8");
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeaders(HttpExchange exchange) {
        Map<String, String> headerMap = serverUrl.getHeaderMap();
        if(headerMap != null && headerMap.size() > 0) {
            Headers responseHeaders = exchange.getResponseHeaders();
            for (String header : headerMap.keySet()) {
                responseHeaders.add(header, headerMap.get(header));
            }
        }
    }

    public static Map<String,String> formData2Dic(String formData ) {
        Map<String,String> result = new HashMap<>();
        if(formData== null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item ->{
            final String[] keyAndVal = item.split("=");
            if( keyAndVal.length == 2) {
                try{
                    final String key = URLDecoder.decode( keyAndVal[0],"utf8");
                    final String val = URLDecoder.decode( keyAndVal[1],"utf8");
                    result.put(key,val);
                }catch (UnsupportedEncodingException ignored) {}
            }
        });
        return result;
    }
}
