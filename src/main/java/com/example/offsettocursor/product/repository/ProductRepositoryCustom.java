package com.example.offsettocursor.product.repository;

import com.example.offsettocursor.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findProductsByCursorQuery(Long lastId, int size);
}