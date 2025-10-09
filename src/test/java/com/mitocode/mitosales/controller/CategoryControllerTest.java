package com.mitocode.mitosales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.controller.CategoryController;
import com.mitocode.dto.CategoryDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Category;
import com.mitocode.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CategoryController.class) // @WebMvcTest(controllers = CategoryController.class) es lo mismo.
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICategoryService service;

    @MockitoBean
    @Qualifier("categoryMapper")
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    Category CATEGORY_1 = new Category(1, "TV", "Televisores", true);
    Category CATEGORY_2 = new Category(2, "CEL", "Celulares", true);
    Category CATEGORY_3 = new Category(3, "LAP", "Laptops", true);

    CategoryDTO CATEGORYDTO_1 = new CategoryDTO(1, "TV", "Televisores", true);
    CategoryDTO CATEGORYDTO_2 = new CategoryDTO(2, "CEL", "Celulares", true);
    CategoryDTO CATEGORYDTO_3 = new CategoryDTO(3, "LAP", "Laptops", true);

    @Test
    public void findAllTest() throws Exception {

        Mockito.when(service.findAll())
                .thenReturn(Arrays.asList(CATEGORY_1, CATEGORY_2, CATEGORY_3));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void findByIdTest() throws Exception {

        final int ID = 1;
        Mockito.when(service.findById(any())).thenReturn(CATEGORY_1);
        Mockito.when(modelMapper.map(CATEGORY_1, CategoryDTO.class)).thenReturn(CATEGORYDTO_1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/categories/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.nameofCategory").value("TV"));
    }

    @Test
    public void saveTest() throws Exception {

        Mockito.when(service.save(any())).thenReturn(CATEGORY_3);
        Mockito.when(modelMapper.map(CATEGORY_3, CategoryDTO.class)).thenReturn(CATEGORYDTO_3);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(CATEGORY_3))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    public void updateTest() throws Exception {

        final int ID = 2;

        Mockito.when(service.update(any(), any())).thenReturn(CATEGORY_2);
        Mockito.when(modelMapper.map(CATEGORY_2, CategoryDTO.class)).thenReturn(CATEGORYDTO_2);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/categories/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CATEGORY_2))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.enabledCategory", is(true)));
    }

    @Test
    public void updateErrorTest() throws Exception {

        final int ID = 99;

        Mockito.when(service.update(any(), any())).thenThrow(new ModelNotFoundException("ID NOT FOUND " + ID));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/categories/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CATEGORY_2))
                ).andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ModelNotFoundException.class, result.getResolvedException()));
    }

    @Test
    public void deleteTest() throws Exception {
        final int ID = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/categories/{id}", ID))
                .andExpect(status()
                        .isNoContent());
    }

    @Test
    public void deleteErrorTest() throws Exception {
        final int ID = 99;

        Mockito.doThrow(new ModelNotFoundException("ID NOT FOUND " + ID))
                        .when(service).delete(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/categories/{id}", ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(ModelNotFoundException.class, result.getResolvedException()));
    }
}
