package com.example.offsettocursor.product.controller;

import com.example.offsettocursor.product.dto.ProductCursorResponseDto;
import com.example.offsettocursor.product.dto.ProductDto;
import com.example.offsettocursor.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.offsettocursor.product.dto.ProductCursorResponseDto;
import com.example.offsettocursor.product.dto.ProductDto;
import com.example.offsettocursor.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductServiceController {

    private final ProductService productService;

    // Offset 기반
    @GetMapping
    public Page<ProductDto> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getPagedProducts(page, size);
    }
    
    // Cursor 기반
    @GetMapping("/cursor")
    public ProductCursorResponseDto getProductsByCursor(
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getProductsByCursor(lastId, size);
    }

    // Cursor 기반 (QueryDSL 적용)
    @GetMapping("/cursor/querydsl")
    public ProductCursorResponseDto getProductsByCursorQueryDSL(
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.getProductsByCursorQueryDSL(lastId, size);
    }

}
