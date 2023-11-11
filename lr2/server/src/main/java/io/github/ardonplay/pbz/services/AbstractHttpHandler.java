package io.github.ardonplay.pbz.services;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractHttpHandler implements HttpHandler {

    protected int buffSize = 1000;

    @Override
    public void handle(HttpExchange exchange) {
        switch (exchange.getRequestMethod()) {
            case "GET" -> getRequest(exchange);
            case "POST" -> postRequest(exchange);
            case "PUT" -> putRequest(exchange);
            case "DELETE" -> deleteRequest(exchange);
            case "OPTIONS" -> optionsRequest(exchange);
            case "HEAD" -> headRequest(exchange);
            case "PATCH" -> patchRequest(exchange);
        }
    }

    protected void getRequest(HttpExchange exchange) {
        methodNotAllowed(exchange);
    }

    protected void postRequest(HttpExchange exchange) {
        methodNotAllowed(exchange);
    }

    protected void putRequest(HttpExchange exchange) {
        methodNotAllowed(exchange);
    }

    protected void deleteRequest(HttpExchange exchange) {
        methodNotAllowed(exchange);
    }

    protected void optionsRequest(HttpExchange exchange) {
        methodNotAllowed(exchange);
    }

    protected void headRequest(HttpExchange exchange) {
        methodNotAllowed(exchange);
    }

    protected void patchRequest(HttpExchange exchange) {
        methodNotAllowed(exchange);
    }

    private void methodNotAllowed(HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(405, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
