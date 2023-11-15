package io.github.ardonplay.pbz.services.impl;

import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.model.dto.CustomerDTO;
import io.github.ardonplay.pbz.model.table.Customer;
import io.github.ardonplay.pbz.repository.table.CustomerRepository;
import io.github.ardonplay.pbz.services.AbstractHttpHandler;
import io.github.ardonplay.pbz.services.HttpController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            public void handle(HttpExchange exchange){
                requestHandlers.put("GET", () -> getRequest(exchange));
                super.handle(exchange);
            }

            public List<CustomerDTO> getRequest(HttpExchange exchange){
                return repository.findAll().stream()
                        .map(customer -> CustomerDTO.builder()
                                .id(customer.getId())
                                .name(customer.getName())
                                .type(customer.getType())
                                .build())
                        .collect(Collectors.toList());
            }
        };
    }
}
