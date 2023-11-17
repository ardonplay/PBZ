package io.github.ardonplay.pbz.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.model.dto.DestinationDTO;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.model.table.*;
import io.github.ardonplay.pbz.repository.table.WaybillRepository;
import io.github.ardonplay.pbz.services.AbstractHttpHandler;
import io.github.ardonplay.pbz.services.HttpController;
import io.github.ardonplay.pbz.services.ResponseEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaybillController implements HttpController {

    private final WaybillRepository repository;

    private final ObjectMapper objectMapper;


    private final ModelMapper modelMapper;

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
                        return new ResponseEntity(repository
                                .findAll()
                                .stream()
                                .map(waybill ->
                                        new WaybillDTO(
                                                waybill.getId(),
                                                waybill.getDate(),
                                                waybill.getCustomer().getId(),
                                                new DestinationDTO(waybill.getDestination().getName())))
                                .collect(Collectors.toList()));
                    }
                    if (requestParams.containsKey("id")) {
                        Waybill waybill = repository.findById(Integer.parseInt(requestParams.get("id"))).orElseThrow(NoSuchElementException::new);
                        System.out.println(waybill.getDate());
                        WaybillDTO waybillDTO = modelMapper.map(waybill, WaybillDTO.class);
                        return new ResponseEntity(waybillDTO);
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
                    byte[] body = readBody(exchange);

                    WaybillDTO waybillDTO = objectMapper.readValue(body, WaybillDTO.class);

                    Waybill waybill = modelMapper.map(waybillDTO, Waybill.class);

                    try {
                        waybill = repository.save(waybill);
                        return new ResponseEntity(waybill.getId());
                    } catch (DataIntegrityViolationException e) {
                        return new ResponseEntity(409);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public ResponseEntity patchRequest(HttpExchange exchange) {
                try {
                    WaybillDTO waybillDTO = objectMapper.readValue(readBody(exchange), WaybillDTO.class);

                    Waybill waybill = repository.findById(waybillDTO.getId()).orElseThrow(NoSuchElementException::new);

                    modelMapper.map(waybillDTO, waybill);
                    repository.save(waybill);
                    return new ResponseEntity(waybillDTO);
                } catch (DataIntegrityViolationException | NoSuchElementException e) {
                    return new ResponseEntity(409);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public ResponseEntity deleteRequest(HttpExchange exchange) {
                try {
                    byte[] body = readBody(exchange);

                    WaybillDTO waybillDTO = objectMapper.readValue(body, WaybillDTO.class);

                    try {
                        repository.deleteById(waybillDTO.getId());
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
