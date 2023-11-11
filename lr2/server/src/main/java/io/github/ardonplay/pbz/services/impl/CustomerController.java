package io.github.ardonplay.pbz.services.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.ardonplay.pbz.model.table.Customer;
import io.github.ardonplay.pbz.repository.table.CustomerRepository;
import io.github.ardonplay.pbz.services.AbstractHttpHandler;
import io.github.ardonplay.pbz.services.HttpController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CustomerController implements HttpController {
    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getPath() {
        return "/api/v1/customers";
    }

    @Override
    public HttpHandler getHandler() {

        return new AbstractHttpHandler() {

            @Override
            public void handle(HttpExchange exchange){
                requestHandlers.put("TEST", () -> customRequest(exchange));
                super.handle(exchange);
            }
            @Override
            protected Object getRequest(HttpExchange exchange) {
                return repository.findAll();
            }

            public Object customRequest(HttpExchange exchange){
                return "custom request method allowed!";
            }
        };
    }
}
