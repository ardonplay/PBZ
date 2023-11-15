package io.github.ardonplay.pbz.services.impl;

import com.sun.net.httpserver.HttpExchange;
import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.repository.table.WaybillRepository;
import io.github.ardonplay.pbz.services.AbstractHttpHandler;
import io.github.ardonplay.pbz.services.HttpController;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WaybillController implements HttpController {

    private final WaybillRepository repository;

    public WaybillController(WaybillRepository repository) {
        this.repository = repository;
    }

    @Override
    public String getPath() {
        return "/api/v1/waybills";
    }

    @Override
    public AbstractHttpHandler getHandler() {

        return new AbstractHttpHandler() {

            @Override
            public void handle(HttpExchange exchange){
                addRequestHandler("GET", () -> getRequest(exchange));
                super.handle(exchange);
            }
            public Object getRequest(HttpExchange exchange) {
                return repository.findAll().stream().map(waybill -> WaybillDTO.builder()
                        .customerID(waybill.getCustomer().getId())
                        .id(waybill.getId())
                        .date(waybill.getDate()).build()).collect(Collectors.toList());
            }

        };
    }
}
