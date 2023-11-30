package io.github.ardonplay.pbz.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.server.utils.controller.HttpController;
import io.github.ardonplay.pbz.server.utils.AbstractHttpHandler;
import io.github.ardonplay.pbz.server.exceptions.BadRequestException;
import io.github.ardonplay.pbz.server.exceptions.NetworkException;
import io.github.ardonplay.pbz.model.dto.CustomerDTO;
import io.github.ardonplay.pbz.server.utils.models.ResponseEntity;
import io.github.ardonplay.pbz.server.utils.models.Wrapper;
import io.github.ardonplay.pbz.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Controller
public class CustomerController implements HttpController {
    private final CustomerService service;

    private final ObjectMapper objectMapper;

    private final int pageSize = 100;

    @Override
    public String getPath() {
        return "/api/v1/customers";
    }

    @Override
    public AbstractHttpHandler getHandler() {

        return new AbstractHttpHandler(objectMapper) {

            @Override
            public ResponseEntity getRequest(HttpExchange exchange) {
                if (requestParams.isEmpty()) {
                    return new ResponseEntity(
                            new Wrapper(
                                    service.getAllCustomers(0, pageSize),
                                    service.getCount()));
                }
                if (requestParams.containsKey("id")) {
                    if (requestParams.get("id").matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
                        return new ResponseEntity(new Wrapper(
                                List.of(service.getCustomerById(UUID.fromString(requestParams.get("id")))), service.getCount()));
                    }
                }
                if (requestParams.containsKey("count") || requestParams.containsKey("page")) {

                    int count = requestParams.getIntValue("count", pageSize);
                    int page = requestParams.getIntValue("page", 0);

                    return new ResponseEntity(new Wrapper(
                            service.getAllCustomers(page, count), service.getCount()));

                }
                throw new BadRequestException();
            }

            @Override
            public ResponseEntity postRequest(HttpExchange exchange) {
                try {
                    CustomerDTO customerDTO = objectMapper.readValue(readBody(exchange), CustomerDTO.class);
                    return new ResponseEntity(service.insertCustomer(customerDTO));
                } catch (IOException e) {
                    log.error(e.getLocalizedMessage());
                    throw new NetworkException();
                }
            }

            @Override
            public ResponseEntity patchRequest(HttpExchange exchange) {
                try {
                    CustomerDTO customerDTO = objectMapper.readValue(readBody(exchange), CustomerDTO.class);
                    return new ResponseEntity(service.updateCustomer(customerDTO));
                } catch (IOException ex) {
                    throw new NetworkException();
                }
            }

            @Override
            public ResponseEntity deleteRequest(HttpExchange exchange) {
                try {
                    CustomerDTO customerDTO = objectMapper.readValue(readBody(exchange), CustomerDTO.class);
                    service.deleteCustomerById(customerDTO);
                    return new ResponseEntity(customerDTO);
                } catch (IOException e) {
                    throw new NetworkException();
                }
            }

        };

    }
}
