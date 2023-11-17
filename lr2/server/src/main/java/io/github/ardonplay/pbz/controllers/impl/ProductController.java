package io.github.ardonplay.pbz.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.model.dto.CustomerDTO;
import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.model.table.Product;
import io.github.ardonplay.pbz.repository.table.ProductRepository;
import io.github.ardonplay.pbz.services.AbstractHttpHandler;
import io.github.ardonplay.pbz.services.HttpController;
import io.github.ardonplay.pbz.services.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductController implements HttpController {

    private final ProductRepository repository;

    private final ModelMapper modelMapper;

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
                try {
                    if (requestParams.isEmpty()) {
                        return new ResponseEntity(repository.findAll().stream().map(repository -> modelMapper.map(repository, ProductDTO.class)).collect(Collectors.toList()));
                    }
                    if (requestParams.containsKey("id")) {
                        return new ResponseEntity(repository.findById(Integer.parseInt(requestParams.get("id"))).map(product -> modelMapper.map(product, ProductDTO.class)).orElseThrow(NoSuchElementException::new));
                    } else {
                        return new ResponseEntity(400);
                    }
                } catch (Exception e) {
                    return new ResponseEntity(400);
                }
            }

            @Override
            protected ResponseEntity postRequest(HttpExchange exchange) {
                try {
                    byte[] body = readBody(exchange);
                    ProductDTO productDTO = objectMapper.readValue(body, ProductDTO.class);
                    Optional<Product> product = repository.findByName(productDTO.getName());
                    if(product.isPresent()){
                      return new ResponseEntity(400);
                    }
                    else {
                        repository.save(modelMapper.map(productDTO, Product.class));
                        return new ResponseEntity(productDTO);
                    }
                } catch (IOException e) {
                    return new ResponseEntity(400);
                }
            }

            @Override
            protected ResponseEntity putRequest(HttpExchange exchange) {
                return super.putRequest(exchange);
            }

            @Override
            protected ResponseEntity deleteRequest(HttpExchange exchange) {
                return super.deleteRequest(exchange);
            }
        };
    }
}
