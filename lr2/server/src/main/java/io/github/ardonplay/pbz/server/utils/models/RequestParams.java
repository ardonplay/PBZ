package io.github.ardonplay.pbz.server.utils.models;

import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.server.exceptions.BadRequestException;

import java.util.HashMap;
import java.util.Map;

public class RequestParams  extends HashMap<String, String>{
    public void setValues(HttpExchange exchange){
        clear();
        putAll(getRequestParams(exchange));
    }
    public int getIntValue(String value, int defaultValue) {
        if(containsKey(value)){
            if(!get(value).matches("-?\\d+")){
                throw new BadRequestException();
            }
            return Integer.parseInt(get(value));
        }
        else {
            return defaultValue;
        }
    }

    public int getIntValue(String value) {
        if(containsKey(value)){
            if(!get(value).matches("-?\\d+")){
                throw new BadRequestException();
            }
            return Integer.parseInt(get(value));
        }
        else {
            throw new BadRequestException();
        }
    }
    private Map<String, String> getRequestParams(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();

        Map<String, String> result = new HashMap<>();
        if(query != null) {
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
        }
        return result;
    }
}
