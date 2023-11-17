package io.github.ardonplay.pbz.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.model.dto.CustomerDTO;
import io.github.ardonplay.pbz.model.table.Customer;
import io.github.ardonplay.pbz.repository.table.CustomerRepository;
import io.github.ardonplay.pbz.services.AbstractHttpHandler;
import io.github.ardonplay.pbz.services.HttpController;
import io.github.ardonplay.pbz.services.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerController implements HttpController {
    private final CustomerRepository repository;

    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

    @Override
    public String getPath() {
        return "/api/v1/customers";
    }

    @Override
    public AbstractHttpHandler getHandler() {

        return new AbstractHttpHandler(objectMapper) {

            @Override
            public ResponseEntity getRequest(HttpExchange exchange) {

                Map<String, String> requestParams = getRequestParams(exchange);

                try {
                    if (requestParams.isEmpty()) {
                        return new ResponseEntity(repository
                                .findAll()
                                .stream()
                                .map(customer ->
                                        new CustomerDTO(customer.getId(), customer.getName(), customer.getType()))
                                .collect(Collectors.toList()));
                    }
                    if (requestParams.containsKey("id")) {
                        return new ResponseEntity(
                                repository
                                        .findById(UUID.fromString(requestParams.get("id")))
                                        .map(customer ->
                                                modelMapper.map(customer, CustomerDTO.class))
                                        .orElseThrow(NoSuchElementException::new));
                    } else {
                        return new ResponseEntity(400);
                    }

                } catch (NoSuchElementException e) {
                    return new ResponseEntity(404);
                }
            }

            @Override
            public ResponseEntity postRequest(HttpExchange exchange) {

                try {
                    byte[] body = readBody(exchange);

                    CustomerDTO customerDTO = objectMapper.readValue(body, CustomerDTO.class);

                    Customer customer = modelMapper.map(customerDTO, Customer.class);

                    try {
                        customer = repository.save(customer);
                        return new ResponseEntity(customer.getId());
                    } catch (DataIntegrityViolationException e) {
                        return new ResponseEntity(409);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public ResponseEntity patchRequest(HttpExchange exchange) {
                try {
                    CustomerDTO customerDTO = objectMapper.readValue(readBody(exchange), CustomerDTO.class);

                    Customer customer = repository.findById(customerDTO.getId()).orElseThrow(NoSuchElementException::new);
                    modelMapper.map(customerDTO, customer);
                    customer = repository.save(customer);
                    return new ResponseEntity(customer.getBankDetails().getName());
                } catch (DataIntegrityViolationException | NoSuchElementException e) {
                    return new ResponseEntity(409);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public ResponseEntity deleteRequest(HttpExchange exchange) {
                try {
                    byte[] body = readBody(exchange);

                    CustomerDTO customerDTO = objectMapper.readValue(body, CustomerDTO.class);

                    try {
                        repository.deleteById(customerDTO.getId());
                        return new ResponseEntity("OK");
                    } catch (DataIntegrityViolationException e) {
                        return new ResponseEntity(409);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        };

    }
}
