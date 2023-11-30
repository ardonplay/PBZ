package io.github.ardonplay.pbz.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.ardonplay.pbz.server.utils.models.RequestParams;
import io.github.ardonplay.pbz.server.utils.models.ResponseEntity;
import io.github.ardonplay.pbz.server.exceptions.BadRequestException;
import io.github.ardonplay.pbz.server.exceptions.NetworkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Supplier;

@Slf4j
@Service
public abstract class AbstractHttpHandler implements HttpHandler {
    protected final Map<String, Supplier<ResponseEntity>> requestHandlers = new HashMap<>();
    protected int buffSize = 1000;

    protected final RequestParams requestParams;
    private final ObjectMapper mapper;

    protected AbstractHttpHandler(ObjectMapper mapper) {
        this.mapper = mapper;
        this.requestParams = new RequestParams();
    }

    @Override
    public void handle(HttpExchange exchange) {

        addRequestHandler("GET", () -> getRequest(exchange));
        addRequestHandler("POST", () -> postRequest(exchange));
        addRequestHandler("HEAD", () -> headRequest(exchange));
        addRequestHandler("OPTIONS", () -> optionsRequest(exchange));
        addRequestHandler("PUT", () -> putRequest(exchange));
        addRequestHandler("DELETE", () -> deleteRequest(exchange));
        addRequestHandler("PATCH", () -> patchRequest(exchange));

        requestParams.setValues(exchange);

        ResponseEntity response;

        try {
            response = this.requestHandlers.computeIfAbsent(exchange.getRequestMethod(), key -> ResponseEntity::new).get();
        }
        catch (NetworkException e){
            e.printStackTrace();
            response = new ResponseEntity(500, "Connection lost!");
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            log.warn(e.getLocalizedMessage());
            response = new ResponseEntity(404);
        }
        catch (BadRequestException e){
            log.warn(e.getLocalizedMessage());
            response = new ResponseEntity(400);
        }
        catch (RuntimeException e){
            e.printStackTrace();
            response = new ResponseEntity(500);
        }
        uploadResponceEntity(exchange, response);
    }


    //Big ball of mud
    protected ResponseEntity getRequest(HttpExchange exchange){
        return new ResponseEntity();
    }

    protected ResponseEntity postRequest(HttpExchange exchange){
        return new ResponseEntity();
    }

    protected ResponseEntity headRequest(HttpExchange exchange){
        return new ResponseEntity();
    }

    protected ResponseEntity patchRequest(HttpExchange exchange){
        return new ResponseEntity();
    }
    protected ResponseEntity putRequest(HttpExchange exchange){
        return new ResponseEntity();
    }

    protected ResponseEntity deleteRequest(HttpExchange exchange){
        return new ResponseEntity();
    }

    protected ResponseEntity optionsRequest(HttpExchange exchange){
        return new ResponseEntity(200);
    }


    protected void addRequestHandler(String type, Supplier<ResponseEntity> func) {
        this.requestHandlers.put(type, func);
    }

    private void uploadResponceEntity(HttpExchange exchange, ResponseEntity responseEntity) {
        try {
            if (responseEntity.getHeaders() != null) {
                exchange.getResponseHeaders().putAll(responseEntity.getHeaders());
            }
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Methods", Collections.singletonList("HEAD, DELETE, OPTIONS, POST, PATCH, GET, PUT"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Headers", Collections.singletonList("X-PINGOTHER, Content-Type"));
            byte[] bytes = new byte[0];

            if (responseEntity.getBody() != null) {
                bytes = mapper.writeValueAsBytes(responseEntity.getBody());
                exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
            }

            exchange.sendResponseHeaders(responseEntity.getStatus(), bytes.length);
            try (OutputStream responseBody = exchange.getResponseBody()) {
                write(responseBody, new ByteArrayInputStream(bytes), bytes.length);
            }finally {
                exchange.getResponseBody().close();
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: " + e.getMessage());
        } catch (IOException e) {
            log.error("Error writing response: " + e.getMessage());
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

    protected byte[] readBody(HttpExchange exchange) throws IOException {
        int length = Integer.parseInt(exchange.getRequestHeaders().get("Content-Length").get(0));
        long readed = 0;

        ByteBuffer byteBuffer = ByteBuffer.allocate(length);

        while (readed != length) {
            byte[] buffer = exchange.getRequestBody().readNBytes(buffSize);
            readed += buffer.length;
            byteBuffer.put(buffer);
        }
        return byteBuffer.array();
    }

}
