package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.DatePriceProductDTO;
import io.github.ardonplay.pbz.model.table.MaxWaybillPerDateView;
import io.github.ardonplay.pbz.repository.table.ViewRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UtilService {
    ViewRepository repository;
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

}
