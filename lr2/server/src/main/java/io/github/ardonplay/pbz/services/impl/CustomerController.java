package io.github.ardonplay.pbz.services.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
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
    public AbstractHttpHandler getHandler() {

        return new AbstractHttpHandler() {
            @Override
            protected void getRequest(HttpExchange exchange) {
                List<Customer> customers = repository.findAll();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String responce = mapper.writeValueAsString(customers);
                    exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                    byte [] bytes = responce.getBytes();
                    System.out.println(bytes.length);
                    exchange.sendResponseHeaders(200,bytes.length);
                    write(exchange.getResponseBody(), new ByteArrayInputStream(bytes), bytes.length);
                    exchange.getResponseBody().close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        };
    }
}
