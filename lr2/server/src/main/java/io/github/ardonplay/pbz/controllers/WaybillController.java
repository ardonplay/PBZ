package io.github.ardonplay.pbz.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.server.utils.controller.HttpController;
import io.github.ardonplay.pbz.server.utils.AbstractHttpHandler;
import io.github.ardonplay.pbz.server.exceptions.BadRequestException;
import io.github.ardonplay.pbz.server.exceptions.NetworkException;
import io.github.ardonplay.pbz.server.utils.models.Wrapper;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.server.utils.models.ResponseEntity;
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
    private final int pageSize = 100;

    @Override
    public String getPath() {
        return "/api/v1/waybills";
    }


    @Override
    public AbstractHttpHandler getHandler() {

        return new AbstractHttpHandler(objectMapper) {

            @Override
            public ResponseEntity getRequest(HttpExchange exchange) {

                if (requestParams.isEmpty()) {
                    return new ResponseEntity(new Wrapper(service.getAllWaybills(0, pageSize), service.getCount()));
                } else if (requestParams.containsKey("count") || requestParams.containsKey("page")) {

                    int count = requestParams.getIntValue("count", pageSize);
                    int page = requestParams.getIntValue("page", 0);

                    return new ResponseEntity(new Wrapper(service.getAllWaybills(page, count), service.getCount()));

                } else if (requestParams.containsKey("id")) {
                    return new ResponseEntity(service.getWaybillById(requestParams.getIntValue("id")));
                }
                throw new BadRequestException();

            }


            @Override
            public ResponseEntity postRequest(HttpExchange exchange) {

                try {
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);
                    return new ResponseEntity(service.insertWaybill(waybillDTO));
                } catch (IOException e) {
                    e.printStackTrace();
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
                    return new ResponseEntity(waybillDTO);
                } catch (IOException e) {
                    throw new NetworkException();
                }
            }

        };
    }
}
