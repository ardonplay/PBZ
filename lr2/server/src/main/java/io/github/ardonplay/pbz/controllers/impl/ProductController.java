package io.github.ardonplay.pbz.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.controllers.HttpController;
import io.github.ardonplay.pbz.controllers.impl.handler.AbstractHttpHandler;
import io.github.ardonplay.pbz.exceptions.BadRequestException;
import io.github.ardonplay.pbz.exceptions.NetworkException;
import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.model.ResponseEntity;
import io.github.ardonplay.pbz.services.ProductService;
import io.github.ardonplay.pbz.services.ProductTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
public class ProductController implements HttpController {
    private final ProductService service;

    private final ObjectMapper objectMapper;

    @Override
    public String getPath() {
        return "/api/v1/products";
    }

    @Override
    public AbstractHttpHandler getHandler() {
        return new AbstractHttpHandler(objectMapper) {
            @Override
            protected ResponseEntity getRequest(HttpExchange exchange) {
                Map<String, String> requestParams = getRequestParams(exchange);
                if (requestParams.isEmpty()) {
                    return new ResponseEntity(service.getAllProducts());
                }
                if (requestParams.containsKey("id")) {
                    return new ResponseEntity(service.getProductById(Integer.parseInt(requestParams.get("id"))));
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
                    return new ResponseEntity("OK");
                } catch (IOException e) {
                    throw new NetworkException();
                }
            }
        };
    }
}
