package io.github.ardonplay.pbz.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.controllers.HttpController;
import io.github.ardonplay.pbz.controllers.impl.handler.AbstractHttpHandler;
import io.github.ardonplay.pbz.exceptions.BadRequestException;
import io.github.ardonplay.pbz.exceptions.NetworkException;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.model.ResponseEntity;
import io.github.ardonplay.pbz.services.WaybillService;
import lombok.AllArgsConstructor;
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

                if (requestParams.isEmpty()) {
                    return new ResponseEntity(service.getAllWaybills());
                } else if (requestParams.containsKey("id")) {
                    if (!requestParams.get("id").matches("-?\\d+")) {
                        throw new BadRequestException();
                    }
                    return new ResponseEntity(service.getWaybillById(Integer.parseInt(requestParams.get("id"))));
                }
                throw new BadRequestException();

            }

            @Override
            public ResponseEntity postRequest(HttpExchange exchange) {

                try {
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);
                    return new ResponseEntity(service.insertWaybill(waybillDTO));
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }

            @Override
            public ResponseEntity patchRequest(HttpExchange exchange) {
                try {
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);
                    return new ResponseEntity(service.updateWaybill(waybillDTO));
                } catch (IOException e) {
                    throw new NetworkException();
                }

            }

            @Override
            public ResponseEntity deleteRequest(HttpExchange exchange) {
                try {
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);

                    service.deleteWaybill(waybillDTO);
                    return new ResponseEntity("OK");

                } catch (IOException e) {
                    throw new NetworkException();
                }
            }

        }

                ;
    }
}
