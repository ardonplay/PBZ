package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.DestinationDTO;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.model.table.Waybill;
import io.github.ardonplay.pbz.repository.table.WaybillRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaybillService {
    private final WaybillRepository repository;

    private final ModelMapper modelMapper;

    public List<WaybillDTO> getAllWaybills() {
        return repository
                .findAll()
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

    public WaybillDTO insertWaybill(WaybillDTO dto) throws DataIntegrityViolationException{
        Waybill waybill = modelMapper.map(dto, Waybill.class);
        waybill = repository.save(waybill);
        return modelMapper.map(waybill, WaybillDTO.class);
    }

    public WaybillDTO updateWaybill(WaybillDTO dto){
        Waybill waybill = repository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
        modelMapper.map(dto, waybill);
        return modelMapper.map(repository.save(waybill), WaybillDTO.class);
    }

    public void deleteWaybill(WaybillDTO dto){
        repository.deleteById(dto.getId());
    }
}
