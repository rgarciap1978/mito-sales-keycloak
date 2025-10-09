package com.mitocode.service;

import com.mitocode.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryService extends ICRUD<Category, Integer> {

    List<Category> findByName(String name);
    List<Category> findByNameLikeIgnoreCase(String name);
    List<Category> findByNameContains(String name);
    List<Category> findByNameStartsWith(String name);
    List<Category> findByNameEndsWith(String name);

    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category> findByNameOrEnabled(String name, boolean enabled);

    List<Category> getNameDescription1(String name,String desc);
    List<Category> getNameDescription2(String name,String desc);

    Page<Category> findPage(Pageable pageable);
    List<Category> findAllOrder(String param);
}