package io.github.ardonplay.pbz.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.controllers.HttpController;
import io.github.ardonplay.pbz.controllers.impl.handler.AbstractHttpHandler;
import io.github.ardonplay.pbz.model.ResponseEntity;
import io.github.ardonplay.pbz.services.ProductTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ProductTypeController implements HttpController {

    private final ObjectMapper objectMapper;

    private final ProductTypeService service;
    @Override
    public String getPath() {
        return "/api/v1/products/types";
    }

    @Override
    public AbstractHttpHandler getHandler() {
        return new AbstractHttpHandler(objectMapper) {
            @Override
            protected ResponseEntity getRequest(HttpExchange exchange) {
                return new ResponseEntity(service.getAllTypes());
            }
        };
    }
}
