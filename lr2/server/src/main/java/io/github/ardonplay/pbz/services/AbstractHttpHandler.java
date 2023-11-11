package io.github.ardonplay.pbz.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractHttpHandler implements HttpHandler {
    protected final Map<String, Supplier<Object>> requestHandlers = new HashMap<>();
    protected int buffSize = 1000;

    @Override
    public void handle(HttpExchange exchange) {

        requestHandlers.put("GET", () -> getRequest(exchange));
        requestHandlers.put("POST", () -> postRequest(exchange));
        requestHandlers.put("PUT", () -> putRequest(exchange));
        requestHandlers.put("DELETE", () -> deleteRequest(exchange));
        requestHandlers.put("OPTIONS", () -> optionsRequest(exchange));
        requestHandlers.put("HEAD", () -> headRequest(exchange));
        requestHandlers.put("PATCH", () -> patchRequest(exchange));

        Object response = requestHandlers.computeIfAbsent(exchange.getRequestMethod(), key -> MethodNotAllowedResponse::new).get();

        uploadResponceEntity(exchange, response);
    }

    protected Object getRequest(HttpExchange exchange) {
        return new MethodNotAllowedResponse();
    }

    protected Object postRequest(HttpExchange exchange) {
        return new MethodNotAllowedResponse();
    }

    protected Object putRequest(HttpExchange exchange) {
        return new MethodNotAllowedResponse();
    }

    protected Object deleteRequest(HttpExchange exchange) {
        return new MethodNotAllowedResponse();
    }

    protected Object optionsRequest(HttpExchange exchange) {
        return new MethodNotAllowedResponse();
    }

    protected Object headRequest(HttpExchange exchange) {
        return new MethodNotAllowedResponse();
    }

    protected Object patchRequest(HttpExchange exchange) {
        return new MethodNotAllowedResponse();
    }

    private void uploadResponceEntity(HttpExchange exchange, Object responseEntity) {
        try {
            if(responseEntity == null) {
                exchange.sendResponseHeaders(200, 0);
            }
            else  if(responseEntity instanceof MethodNotAllowedResponse) {
                exchange.sendResponseHeaders(405, 0);
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                byte[] bytes = mapper.writeValueAsBytes(responseEntity);

                exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                exchange.sendResponseHeaders(200, bytes.length);

                write(exchange.getResponseBody(), new ByteArrayInputStream(bytes), bytes.length);
            }
            exchange.getResponseBody().close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    protected void write(OutputStream writtable, InputStream readable, long length) throws IOException {
        long writted = 0;
        while (writted != length) {
            byte[] buffer = readable.readNBytes(buffSize);
            writtable.write(buffer);
            writted += buffer.length;
        }
        readable.close();
    }

}
