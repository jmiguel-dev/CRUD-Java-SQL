package com.example.crud_javasql.repositories;

import com.example.crud_javasql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
