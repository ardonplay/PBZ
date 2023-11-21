package io.github.ardonplay.pbz.services;


import io.github.ardonplay.pbz.model.dto.ProductDTO;
import io.github.ardonplay.pbz.model.table.Product;
import io.github.ardonplay.pbz.repository.table.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;

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
        Product product = modelMapper.map(dto, Product.class);
        product = repository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    public ProductDTO updateProduct(ProductDTO dto){
        Product product = repository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
        modelMapper.map(dto, product);
        return modelMapper.map(repository.save(product), ProductDTO.class);
    }

    public void deleteProduct(ProductDTO dto){
        repository.deleteById(dto.getId());
    }
}

