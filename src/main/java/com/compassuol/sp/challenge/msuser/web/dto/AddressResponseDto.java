package com.compassuol.sp.challenge.msuser.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressResponseDto {

    private String cep;
    private String uf;
    private Integer ddd;
    private String localidade;
    private String logradouro;
    private String complemento;

    public void setAddressFromDto(AddressDto addressDto) {
        this.setCep(addressDto.getCep());
        this.setUf(addressDto.getUf());
        this.setDdd(addressDto.getDdd());
        this.setLocalidade(addressDto.getLocalidade());
        this.setLogradouro(addressDto.getLogradouro());
        this.setComplemento(addressDto.getComplemento());
    }
}
