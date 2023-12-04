package io.github.ardonplay.pbz.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.server.utils.AbstractHttpHandler;
import io.github.ardonplay.pbz.server.utils.controller.HttpController;
import io.github.ardonplay.pbz.server.utils.models.ResponseEntity;
import io.github.ardonplay.pbz.services.UtilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Controller
@AllArgsConstructor
public class UtilController implements HttpController {

    private final ObjectMapper objectMapper;

    private final UtilService service;

    @Override
    public String getPath() {
        return "/api/v1/utils";
    }

    @Override
    public AbstractHttpHandler getHandler() {
        return new AbstractHttpHandler(objectMapper) {
            @Override
            protected ResponseEntity getRequest(HttpExchange exchange) {
                switch (exchange.getRequestURI().getPath().replace(getPath(), "")) {
                    case "/find_price_by_product_id" -> {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            return new ResponseEntity(service.getPriceListOfProductPerDate(requestParams.getIntValue("id"), dateFormat.parse(requestParams.get("date_start")), dateFormat.parse(requestParams.get("date_end"))));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case "/max_waybills_per_date" -> {
                        return new ResponseEntity(service.getMaxWaybillsPerDate());
                    }

                }
                return new ResponseEntity();
            }
        };
    }
}
