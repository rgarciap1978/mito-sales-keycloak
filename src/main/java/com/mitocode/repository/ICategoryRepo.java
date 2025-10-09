package com.mitocode.repository;

import com.mitocode.model.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepo extends IGenericRepo<Category, Integer> {

    // Queries derivados
    List<Category> findByName(String name);
    List<Category> findByNameLikeIgnoreCase(String name);
    List<Category> findByNameContains(String name);
    List<Category> findByNameStartsWith(String name);
    List<Category> findByNameEndsWith(String name);

    List<Category> findByNameAndEnabled(String name, boolean enabled);
    List<Category> findByNameOrEnabled(String name, boolean enabled);

//    List<Category> findByEnabled(boolean enabled);
//    List<Category> findByEnabledTrue();
//    List<Category> findByEnabledFalse();

//    Optional<Category> findOneByName(String name);
//    List<Category> findTop3ByName(String name);

//    List<Category> frindByNameIs(String name);
//    List<Category> frindByNameIsNot(String name);
//    List<Category> frindByNameIsNull();
//    List<Category> frindByNameIsNotNull();
//    List<Category> findByNameEqualsIgnoreCase(String name);
//    List<Category> findByIdCategoryLessThan(Integer idCategory);
//    List<Category> findByIdCategoryLessThanEqual(Integer idCategory);
//    List<Category> findByIdCategoryGreaterThan(Integer idCategory);
//    List<Category> findByIdCategoryGreaterThanEqual(Integer idCategory);
//    List<Category> findByIdCategoryBetween(Integer start, Integer end);
//    List<Category> findByNameOrderBYDescription(Integer start, Integer end);
//
//
    // JPQL Queries
    @Query("FROM Category c WHERE c.name = :name AND c.description LIKE %:desc%")
    List<Category> getNameDescription1(@Param("name") String name,@Param("desc") String desc);

    @Query("SELECT new Category(c.name, c.enabled) FROM Category c WHERE c.name = :name AND c.description LIKE %:desc%")
    List<Category> getNameDescription2(@Param("name") String name,@Param("desc") String desc);

    // SQL Native Queries
    @Query(value = "SELECT * FROM category c WHERE c.name = :name", nativeQuery = true)
    List<Category> getNameSQL(@Param("name") String name);

    @Modifying
    @Query(value = "UPDATE category c SET c.name = :name WHERE c.id_category = :idCategory", nativeQuery = true)
    Integer updateName(@Param("name") String name, @Param("idCategory") Integer idCategory);
}
