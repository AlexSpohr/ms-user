package com.compassuol.sp.challenge.msuser.web.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "address-consumer", url = "localhost:8081/v1/address")
public interface UserConsumerAddress {
    @PostMapping(value = "/{cep}")
    void saveAddress(@PathVariable String cep);
}
