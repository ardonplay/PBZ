package io.github.ardonplay.pbz.services;

import io.github.ardonplay.pbz.model.dto.CustomerDTO;
import io.github.ardonplay.pbz.model.table.Customer;
import io.github.ardonplay.pbz.repository.table.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    private final ModelMapper modelMapper;


    public List<CustomerDTO> getAllCustomers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository
                .findAll(pageable)
                .stream()
                .map(customer ->
                        new CustomerDTO(customer.getId(), customer.getName(), customer.getType()))
                .collect(Collectors.toList());
    }

    public Long getCount() {
        return repository.count();
    }

    public CustomerDTO getCustomerById(UUID uuid) {
        return repository
                .findById(uuid)
                .map(customer ->
                        modelMapper.map(customer, CustomerDTO.class))
                .orElseThrow(NoSuchElementException::new);
    }

    public CustomerDTO insertCustomer(CustomerDTO dto) {
        Customer customer = modelMapper.map(dto, Customer.class);
        customer = repository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(CustomerDTO dto){
        Customer customer = repository.findById(dto.getId()).orElseThrow(NoSuchElementException::new);
        modelMapper.map(dto, customer);
        return dto;
    }

    public void deleteCustomerById(CustomerDTO dto){
        repository.deleteById(dto.getId());
    }

}
