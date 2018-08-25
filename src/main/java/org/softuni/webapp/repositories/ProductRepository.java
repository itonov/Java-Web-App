package org.softuni.webapp.repositories;

import org.softuni.webapp.domain.entities.enums.ProductCategory;
import org.softuni.webapp.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product AS p WHERE p.id = :id")
    Product findProductById(@Param("id") String id);

    List<Product> findAllProductsByProductCategory(ProductCategory productCategory);
}
