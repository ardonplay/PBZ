package io.github.ardonplay.pbz.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.controllers.HttpController;
import io.github.ardonplay.pbz.controllers.impl.handler.AbstractHttpHandler;
import io.github.ardonplay.pbz.exceptions.BadRequestException;
import io.github.ardonplay.pbz.exceptions.NetworkException;
import io.github.ardonplay.pbz.model.dto.CustomerDTO;
import io.github.ardonplay.pbz.model.ResponseEntity;
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

                if (requestParams.isEmpty()) {
                    return new ResponseEntity(service.getAllCustomers());
                }
                if (requestParams.containsKey("id")) {
                    if (requestParams.get("id").matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
                        return new ResponseEntity(
                                service.getCustomerById(UUID.fromString(requestParams.get("id"))));
                    }
                }
                throw new BadRequestException();
            }

            @Override
            public ResponseEntity postRequest(HttpExchange exchange) {
                try {
                    CustomerDTO customerDTO = objectMapper.readValue(readBody(exchange), CustomerDTO.class);
                    return new ResponseEntity(service.insertCustomer(customerDTO));
                } catch (IOException e) {
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
                    return new ResponseEntity(200);
                } catch (IOException e) {
                    throw new NetworkException();
                }
            }

        };

    }
}
