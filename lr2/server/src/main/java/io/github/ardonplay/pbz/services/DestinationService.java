package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.DestinationDTO;
import io.github.ardonplay.pbz.repository.table.DestinationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DestinationService {
    private final DestinationRepository repository;

    private final ModelMapper modelMapper;

    public List<DestinationDTO> getAllDestinations(){
        return repository.findAll().stream().map(destination -> modelMapper.map(destination, DestinationDTO.class)).collect(Collectors.toList());
    }
}
