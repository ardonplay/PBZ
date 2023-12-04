package io.github.ardonplay.pbz.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.ardonplay.pbz.model.dto.DestinationDTO;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.model.dto.WaybillProductDTO;
import io.github.ardonplay.pbz.model.table.Customer;
import io.github.ardonplay.pbz.model.table.Destination;
import io.github.ardonplay.pbz.model.table.Waybill;
import io.github.ardonplay.pbz.model.table.WaybillProduct;
import io.github.ardonplay.pbz.repository.table.CustomerRepository;
import io.github.ardonplay.pbz.repository.table.DestinationRepository;
import io.github.ardonplay.pbz.repository.table.WaybillProductRepository;
import io.github.ardonplay.pbz.repository.table.WaybillRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaybillService {
    private final WaybillRepository repository;
    private final ObjectMapper mapper;
    private final ModelMapper modelMapper;

    public Long getCount() {
        return repository.count();
    }

    public List<WaybillDTO> getAllWaybills(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository
                .findAll(pageable)
                .stream()
                .map(waybill ->
                        new WaybillDTO(
                                waybill.getId(),
                                waybill.getDate(),
                                waybill.getCustomer().getId(),
                                new DestinationDTO(waybill.getDestination().getName())))
                .collect(Collectors.toList());
    }


    public WaybillDTO getWaybillById(int id) {
        return modelMapper.map(repository.findById(id).orElseThrow(NoSuchElementException::new), WaybillDTO.class);
    }

    public WaybillDTO insertWaybill(WaybillDTO dto) throws DataIntegrityViolationException, JsonProcessingException {
        int result = repository.addNewWaybill(dto.getCustomerID(), dto.getDate(), dto.getDestination().getId(), convertToSimpleWaybillProductDTO(dto));
        return modelMapper.map(repository.findById(result).orElseThrow(NoSuchElementException::new), WaybillDTO.class);
    }

    public WaybillDTO updateWaybill(WaybillDTO dto) throws JsonProcessingException {
        repository.updateWaybill(dto.getId(), dto.getCustomerID(), dto.getDate(), dto.getDestination().getId(), convertToSimpleWaybillProductDTO(dto));
        return dto;
    }

    public void deleteWaybill(WaybillDTO dto) {
        repository.deleteWaybill(dto.getId());
    }

    private String convertToSimpleWaybillProductDTO(WaybillDTO dto) throws JsonProcessingException {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        dto.getWaybillProducts().forEach(waybillProductDTO -> {
            waybillProductDTO.setId(waybillProductDTO.getProduct().getId());
            waybillProductDTO.setProduct(null);
        });
        return ow.writeValueAsString(dto.getWaybillProducts());
    }
}
