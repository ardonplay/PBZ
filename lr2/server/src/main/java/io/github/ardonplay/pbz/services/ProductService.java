package io.github.ardonplay.pbz.services;


import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.model.dto.ProductTypeDTO;
import io.github.ardonplay.pbz.model.table.Product;
import io.github.ardonplay.pbz.model.table.ProductType;
import io.github.ardonplay.pbz.repository.table.ProductRepository;
import io.github.ardonplay.pbz.repository.table.ProductTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository
                .findAll(pageable)
                .stream()
                .map(product ->
                        new ProductDTO(
                                product.getId(),
                                product.getName(),
                                new ProductTypeDTO(product.getProductType().getId(), product.getProductType().getName())))
                .collect(Collectors.toList());
    }

    public Long getCount() {
        return repository.count();
    }

    public ProductDTO getProductById(int id) {
        return modelMapper.map(repository.findById(id).orElseThrow(NoSuchElementException::new), ProductDTO.class);
    }

    public ProductDTO insertProduct(ProductDTO dto) throws DataIntegrityViolationException {
        Product product = modelMapper.map(dto, Product.class);
        product = repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    public ProductDTO updateProduct(ProductDTO dto) {
        Product product = repository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
        modelMapper.map(dto, product);
        product = repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    public void deleteProduct(ProductDTO dto) {
        repository.deleteById(dto.getId());
    }
}

