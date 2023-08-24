package com.auctionapp.repository;

import com.auctionapp.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%', :name, '%'))")
    List<Product> findByAllNames(String name);

}
