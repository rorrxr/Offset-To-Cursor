package com.example.offsettocursor.product.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCursorRequestDto {
    private Long lastId; // 커서 기준 ID
    private int size = 10;
}