package com.mitocode.repository;

import com.mitocode.model.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepo extends IGenericRepo<Product,Integer> {

    @Query("FROM Product p WHERE p.category.name = :name")
    List<Product> getProductsByCategory(String name);
}
