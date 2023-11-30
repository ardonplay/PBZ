package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.model.dto.ProductTypeDTO;
import io.github.ardonplay.pbz.repository.table.ProductTypeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductTypeService {
    private final ProductTypeRepository repository;

    private final ModelMapper modelMapper;
    public Long getCount() {
        return repository.count();
    }

    public List<ProductTypeDTO> getAllTypes(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(pageable).stream().map(type -> modelMapper.map(type, ProductTypeDTO.class)).collect(Collectors.toList());
    }

}
