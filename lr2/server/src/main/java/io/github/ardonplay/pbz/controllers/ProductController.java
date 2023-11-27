package io.github.ardonplay.pbz.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.server.utils.controller.HttpController;
import io.github.ardonplay.pbz.server.utils.AbstractHttpHandler;
import io.github.ardonplay.pbz.server.exceptions.BadRequestException;
import io.github.ardonplay.pbz.server.exceptions.NetworkException;
import io.github.ardonplay.pbz.server.utils.models.Wrapper;
import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.server.utils.models.ResponseEntity;
import io.github.ardonplay.pbz.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
public class ProductController implements HttpController {
    private final ProductService service;

    private final ObjectMapper objectMapper;

    private final int pageSize = 3;
    @Override
    public String getPath() {
        return "/api/v1/products";
    }

    @Override
    public AbstractHttpHandler getHandler() {
        return new AbstractHttpHandler(objectMapper) {
            @Override
            protected ResponseEntity getRequest(HttpExchange exchange) {
                if (requestParams.isEmpty()) {
                    return new ResponseEntity(new Wrapper(Collections.singletonList(service.getAllProducts(0, pageSize)), service.getCount()));
                }
                if (requestParams.containsKey("count") || requestParams.containsKey("page")) {

                    int count = requestParams.getIntValue("count", pageSize);
                    int page = requestParams.getIntValue("page", 0);

                    return new ResponseEntity(new Wrapper(Collections.singletonList(service.getAllProducts(page, count)), service.getCount()));

                }
                if (requestParams.containsKey("id")) {
                    return new ResponseEntity(new Wrapper(List.of(service.getProductById(Integer.parseInt(requestParams.get("id")))), service.getCount()));
                }
                throw new BadRequestException();
            }

            @Override
            protected ResponseEntity postRequest(HttpExchange exchange) {
                try {
                    ProductDTO productDTO = objectMapper.readValue(readBody(exchange), ProductDTO.class);

                    System.out.println(productDTO);
                    return new ResponseEntity(service.insertProduct(productDTO));
                } catch (IOException e) {
                    throw new NetworkException();
                }
            }

            @Override
            protected ResponseEntity patchRequest(HttpExchange exchange) {
                try {
                    ProductDTO productDTO = objectMapper.readValue(readBody(exchange), ProductDTO.class);
                    return new ResponseEntity(service.updateProduct(productDTO));
                } catch (IOException e) {
                    throw new NetworkException();
                }
            }

            @Override
            protected ResponseEntity deleteRequest(HttpExchange exchange) {
                try {
                    ProductDTO productDTO = objectMapper.readValue(readBody(exchange), ProductDTO.class);
                    service.deleteProduct(productDTO);
                    return new ResponseEntity(productDTO);
                } catch (IOException e) {
                    throw new NetworkException();
                }
            }
        };
    }
}
