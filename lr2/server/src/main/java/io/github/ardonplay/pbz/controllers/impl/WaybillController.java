package io.github.ardonplay.pbz.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.controllers.HttpController;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.services.AbstractHttpHandler;
import io.github.ardonplay.pbz.model.ResponseEntity;
import io.github.ardonplay.pbz.services.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller
@AllArgsConstructor
public class WaybillController implements HttpController {

    private final ObjectMapper objectMapper;

    private final WaybillService service;

    @Override
    public String getPath() {
        return "/api/v1/waybills";
    }


    @Override
    public AbstractHttpHandler getHandler() {

        return new AbstractHttpHandler(objectMapper) {

            @Override
            public ResponseEntity getRequest(HttpExchange exchange) {

                Map<String, String> requestParams = getRequestParams(exchange);

                try {
                    if (requestParams.isEmpty()) {
                        return new ResponseEntity(service.getAllWaybills());
                    }
                    if (requestParams.containsKey("id")) {

                        return new ResponseEntity(service.getWaybillById(Integer.parseInt(requestParams.get("id"))));
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
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);
                    return new ResponseEntity(service.insertWaybill(waybillDTO));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public ResponseEntity patchRequest(HttpExchange exchange) {
                try {
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);
                    return new ResponseEntity(service.updateWaybill(waybillDTO));
                } catch (DataIntegrityViolationException | NoSuchElementException e) {
                    return new ResponseEntity(409);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public ResponseEntity deleteRequest(HttpExchange exchange) {
                try {
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);

                    try {
                        service.deleteWaybill(waybillDTO);
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
