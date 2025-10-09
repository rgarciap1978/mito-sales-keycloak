package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private Integer idClient;

    @NotEmpty
    private String firstNameClient;

    @NotEmpty
    private String lastNameClient;

    @NotEmpty
    private String countryClient;

    @NotEmpty
    private String idCard;

    @NotEmpty
    private String phoneNumberClient;

    @NotEmpty
    @Email
    private String emailClient;

    @NotEmpty
    private String addressClient;
}
