package com.mitocode.config;

import com.mitocode.dto.CategoryDTO;
import com.mitocode.dto.ProductDTO;
import com.mitocode.model.Category;
import com.mitocode.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }

    @Bean("categoryMapper")
    public ModelMapper categoryMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Handle Mismatched Fields
        modelMapper.createTypeMap(Category.class, CategoryDTO.class)
                .addMapping(Category::getName, (dest, v) -> dest.setNameofCategory((String) v));

        modelMapper.createTypeMap(CategoryDTO.class, Category.class)
                .addMapping(CategoryDTO::getNameofCategory, (dest, v) -> dest.setName((String) v));

        return modelMapper;
    }
}
