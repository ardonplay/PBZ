package io.github.ardonplay.pbz.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.server.exceptions.BadRequestException;
import io.github.ardonplay.pbz.server.utils.controller.HttpController;
import io.github.ardonplay.pbz.server.utils.AbstractHttpHandler;
import io.github.ardonplay.pbz.server.utils.models.ResponseEntity;
import io.github.ardonplay.pbz.server.utils.models.Wrapper;
import io.github.ardonplay.pbz.services.ProductTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Collections;

@Controller
@AllArgsConstructor
public class ProductTypeController implements HttpController {

    private final ObjectMapper objectMapper;

    private final ProductTypeService service;
    private final int pageSize = 100;

    @Override
    public String getPath() {
        return "/api/v1/products/types";
    }

    @Override
    public AbstractHttpHandler getHandler() {
        return new AbstractHttpHandler(objectMapper) {
            @Override
            protected ResponseEntity getRequest(HttpExchange exchange) {
                if (requestParams.isEmpty()) {
                    return new ResponseEntity(new Wrapper(service.getAllTypes(0, pageSize), service.getCount()));

                } else if (requestParams.containsKey("count") || requestParams.containsKey("page")) {

                    int count = requestParams.getIntValue("count", pageSize);
                    int page = requestParams.getIntValue("page", 0);

                    return new ResponseEntity(new Wrapper(service.getAllTypes(page, count), service.getCount()));

                }
                throw new BadRequestException();
            }
        };
    }
}
