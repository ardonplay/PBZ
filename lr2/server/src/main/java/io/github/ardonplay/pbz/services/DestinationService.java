package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.DestinationDTO;
import io.github.ardonplay.pbz.repository.table.DestinationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DestinationService {
    private final DestinationRepository repository;

    private final ModelMapper modelMapper;

    public Long getCount() {
        return repository.count();
    }
    public List<DestinationDTO> getAllDestinations(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(pageable)
                .stream()
                .map(destination -> modelMapper.map(destination, DestinationDTO.class))
                .collect(Collectors.toList());
    }
}
