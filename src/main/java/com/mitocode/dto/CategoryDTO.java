package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    private Integer idCategory;

    @NonNull
    //@NotEmpty
    //@NotBlank
    @Size(min = 3, max = 50)
    private String nameofCategory;

    @Size(min = 3, max = 50)
    private String descriptionCategory;

    @NonNull
    private boolean enabledCategory;

    /*@Max(value = 100)
    @Min(value = 1)
    private int age;

    @Email
    private String email;

    @Pattern(regexp = "\\d{10}")
    private String phone;*/
}
