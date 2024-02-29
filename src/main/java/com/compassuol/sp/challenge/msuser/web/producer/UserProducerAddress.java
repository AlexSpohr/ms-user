package com.compassuol.sp.challenge.msuser.web.producer;

import com.compassuol.sp.challenge.msuser.web.dto.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "ms-address", url = "localhost:8081/v1/address")
public interface UserProducerAddress {
    @PostMapping(value = "/{cep}")
    AddressDto saveAddress(@PathVariable String cep, @RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    AddressDto getAddressById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token);

}
