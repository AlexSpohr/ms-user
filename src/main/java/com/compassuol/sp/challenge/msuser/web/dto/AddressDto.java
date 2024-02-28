package com.compassuol.sp.challenge.msuser.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private Long id;
    private String cep;
    private String uf;
    private Integer ddd;
    private String localidade;
    private String logradouro;
    private String complemento;
}
