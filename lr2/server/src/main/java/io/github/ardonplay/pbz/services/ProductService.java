package io.github.ardonplay.pbz.services;


import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.model.table.Product;
import io.github.ardonplay.pbz.model.table.ProductType;
import io.github.ardonplay.pbz.repository.table.ProductRepository;
import io.github.ardonplay.pbz.repository.table.ProductTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductTypeRepository typeRepository;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        return repository
                .findAll()
                .stream()
                .map(product ->
                        new ProductDTO(
                                product.getId(),
                                product.getName(),
                                product.getProductType().getType()))
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(int id) {
        return modelMapper.map(repository.findById(id).orElseThrow(NoSuchElementException::new), ProductDTO.class);
    }

    public ProductDTO insertProduct(ProductDTO dto) throws DataIntegrityViolationException{
        ProductType type = typeRepository.findByType(dto.getType());

        Product product = modelMapper.map(dto, Product.class);
        product.setProductType(type);
        product = repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    public ProductDTO updateProduct(ProductDTO dto){
        Product product = repository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
        modelMapper.map(dto, product);
        product = repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    public void deleteProduct(ProductDTO dto){
        repository.deleteById(dto.getId());
    }
}

