package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.DestinationDTO;
import io.github.ardonplay.pbz.model.dto.WaybillDTO;
import io.github.ardonplay.pbz.model.dto.WaybillProductDTO;
import io.github.ardonplay.pbz.model.table.Customer;
import io.github.ardonplay.pbz.model.table.Destination;
import io.github.ardonplay.pbz.model.table.Waybill;
import io.github.ardonplay.pbz.model.table.WaybillProduct;
import io.github.ardonplay.pbz.repository.table.CustomerRepository;
import io.github.ardonplay.pbz.repository.table.DestinationRepository;
import io.github.ardonplay.pbz.repository.table.WaybillProductRepository;
import io.github.ardonplay.pbz.repository.table.WaybillRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaybillService {
    private final WaybillRepository repository;
    private final CustomerRepository customerRepository;
    private final DestinationRepository destinationRepository;
    private final WaybillProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Long getCount() {
        return repository.count();
    }

    public List<WaybillDTO> getAllWaybills(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository
                .findAll(pageable)
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

    public WaybillDTO insertWaybill(WaybillDTO dto) throws DataIntegrityViolationException {
        Waybill waybill = new Waybill();
        waybill.setDate(dto.getDate());
        Customer customer = customerRepository.findById(dto.getCustomerID()).orElseThrow(NoSuchElementException::new);

        Destination destination = destinationRepository.findById(dto.getDestination().getId()).orElseThrow(NoSuchElementException::new);

        waybill.setDestination(destination);
        waybill.setCustomer(customer);
        Set<WaybillProduct> products = dto.getWaybillProducts().stream().map(waybillProduct -> modelMapper.map(waybillProduct, WaybillProduct.class)).collect(Collectors.toSet());

        for(var product: products){
            product.setWaybill(waybill);
        }

        waybill.setWaybillProducts(products);
        waybill = repository.save(waybill);
        return modelMapper.map(waybill, WaybillDTO.class);
    }


    @Transactional
    public WaybillDTO updateWaybill(WaybillDTO dto) {

        Waybill waybill = repository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);

        Customer customer = customerRepository.findById(dto.getCustomerID()).orElseThrow(NoSuchElementException::new);

        Destination destination = destinationRepository.findById(dto.getDestination().getId()).orElseThrow(NoSuchElementException::new);

        waybill.setDestination(destination);
        waybill.setCustomer(customer);
        List<WaybillProduct> products = dto.getWaybillProducts().stream().map(waybillProduct -> modelMapper.map(waybillProduct, WaybillProduct.class)).toList();

        for(var product: products){
            product.setWaybill(waybill);
        }

        Set<WaybillProduct> waybillProducts = waybill.getWaybillProducts();
        List<WaybillProduct> waybillProductsToDelete = new ArrayList<>();

        for(var product: waybillProducts){
            if(products.contains(product)){
                waybillProducts.add(product);
            }
            else {
                waybillProductsToDelete.add(product);
            }
        }
        waybillProducts.addAll(products);
        waybillProductsToDelete.forEach(waybillProducts::remove);
        productRepository.deleteAll(waybillProductsToDelete);

        waybill = repository.save(waybill);
        return modelMapper.map(waybill, WaybillDTO.class);
    }

    public void deleteWaybill(WaybillDTO dto) {
        repository.deleteById(dto.getId());
    }
}
