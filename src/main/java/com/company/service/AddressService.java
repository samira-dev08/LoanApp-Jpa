package com.company.service;


import com.company.dto.request.AddressRequest;
import com.company.entity.Address;
import com.company.mapper.AddressMapper;
import com.company.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;


    public Address create(AddressRequest addressRequest) {
        Address address= addressMapper.toAddress(addressRequest);
       return addressRepository.save(address);
    }
}
