package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.repository.table.ProductTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductTypeService {
    private final ProductTypeRepository repository;

    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllTypes(){
        return repository.findAll().stream().map(type -> modelMapper.map(type, ProductDTO.class)).collect(Collectors.toList());
    }

}
