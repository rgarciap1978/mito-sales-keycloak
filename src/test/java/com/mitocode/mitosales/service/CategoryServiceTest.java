package com.mitocode.mitosales.service;

import com.mitocode.model.Category;
import com.mitocode.repository.ICategoryRepo;
import com.mitocode.service.ICategoryService;
import com.mitocode.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @MockitoBean
    private ICategoryRepo repo;

    @MockitoBean
    private ICategoryService service;

    private Category CATEGORY_1;
    private Category CATEGORY_2;
    private Category CATEGORY_3;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
        this.service = new CategoryServiceImpl(repo);

        CATEGORY_1 = new Category(1, "TV", "Televisores", true);
        CATEGORY_2 = new Category(2, "CEL", "Celulares", true);
        CATEGORY_3 = new Category(3, "LAP", "Laptops", true);

        Mockito.when(repo.findAll()).thenReturn(List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3));
        Mockito.when(repo.save(any())).thenReturn(CATEGORY_1);
        Mockito.when(repo.findById(1)).thenReturn(Optional.of(CATEGORY_1));
    }

    @Test
    void findAllTest() throws Exception {
        //Mockito.when(repo.findAll()).thenReturn(List.of(CATEGORY_1, CATEGORY_2, CATEGORY_3));
        List<Category> response = service.findAll();

        //assertNotNull(response);
        //assertTrue(response.isEmpty());
        assertFalse(response.isEmpty());
    }

    @Test
    void findByIdTest() throws Exception {
        final int ID = 1;

        //Mockito.when(repo.findById(ID)).thenReturn(java.util.Optional.of(CATEGORY_1));

        Category response = service.findById(ID);

        assertNotNull(response);
        assertEquals("TV", response.getName());
        assertInstanceOf(Category.class, response);
    }

    @Test
    void saveTest() throws Exception {
        //Mockito.when(repo.save(any())).thenReturn(CATEGORY_1);

        Category response = service.save(CATEGORY_1);

        assertNotNull(response);
    }

    @Test
    void deleteTest() throws Exception {
        final int ID = 1;
        //Mockito.when(repo.findById(ID)).thenReturn(Optional.of(CATEGORY_1));

        service.delete(ID);
        service.delete(ID);
        service.delete(ID);

        //verify(repo, times(1)).deleteById(ID);
        verify(repo, atLeast(2)).deleteById(ID);
        //verify(repo, atMost(3)).deleteById(ID);
        //verify(repo, never()).deleteById(ID);
    }
}
