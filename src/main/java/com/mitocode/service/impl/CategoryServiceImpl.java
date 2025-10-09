package com.mitocode.service.impl;

import com.mitocode.model.Category;
import com.mitocode.repository.ICategoryRepo;
import com.mitocode.repository.IGenericRepo;
import com.mitocode.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        extends CRUDImpl<Category, Integer>
        implements ICategoryService {

    private final ICategoryRepo repo;

    @Override
    protected IGenericRepo<Category, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Category> findByName(String name) {
        return repo.findByName(name);
    }

    @Override
    public List<Category> findByNameLikeIgnoreCase(String name) {
        return repo.findByNameLikeIgnoreCase('%' + name + '%');
    }

    @Override
    public List<Category> findByNameContains(String name) {
        return repo.findByNameContains(name);
    }

    @Override
    public List<Category> findByNameStartsWith(String name) {
        return repo.findByNameStartsWith(name);
    }

    @Override
    public List<Category> findByNameEndsWith(String name) {
        return repo.findByNameEndsWith(name);
    }

    @Override
    public List<Category> findByNameAndEnabled(String name, boolean enabled) {
        return repo.findByNameAndEnabled(name, enabled);
    }

    @Override
    public List<Category> findByNameOrEnabled(String name, boolean enabled) {
        return repo.findByNameOrEnabled(name, enabled);
    }

    @Override
    public List<Category> getNameDescription1(String name, String desc) {
        return repo.getNameDescription1(name, desc);
    }

    @Override
    public List<Category> getNameDescription2(String name, String desc) {
        return repo.getNameDescription2(name, desc);
    }

    @Override
    public Page<Category> findPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public List<Category> findAllOrder(String param) {
        Sort.Direction direction = param.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        return repo.findAll(Sort.by(direction, "name"));
    }

}
