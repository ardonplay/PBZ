package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.DatePriceProductDTO;
import io.github.ardonplay.pbz.model.dto.DestinationDTO;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.model.table.MaxWaybillPerDateView;
import io.github.ardonplay.pbz.repository.table.DestinationRepository;
import io.github.ardonplay.pbz.repository.table.ViewRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UtilService {
    ViewRepository repository;
    DestinationRepository destinationRepository;
    ModelMapper mapper;

    public List<MaxWaybillPerDateView> getMaxWaybillsPerDate() {
        return repository.findAll();
    }

    public List<DatePriceProductDTO> getPriceListOfProductPerDate(int id, Date start, Date end) {
        return repository.findPriceByProductId(id, start, end).stream().map(
                (record) -> {
                    DatePriceProductDTO datePriceProductDTO = new DatePriceProductDTO();
                    datePriceProductDTO.setName((String) record[0]);
                    datePriceProductDTO.setDate((Date) record[1]);
                    datePriceProductDTO.setPrice((BigDecimal) record[2]);
                    return datePriceProductDTO;
                }
        ).toList();
    }

    public List<WaybillDTO> getWaybillsByProduct(int productId) {
        return repository.getWaybillWithProduct(productId).stream().map(
                (record) -> {
                    WaybillDTO waybiilDTO = new WaybillDTO();
                    waybiilDTO.setId((int) record[0]);
                    waybiilDTO.setCustomerID((UUID) record[1]);
                    waybiilDTO.setDate((Date) record[2]);
                    waybiilDTO.setDestination(mapper.map(destinationRepository.findById((int) record[3]).orElseThrow(), DestinationDTO.class));
                    return waybiilDTO;
                }
        ).toList();
    }

}
