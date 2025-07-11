package com.example.offsettocursor.product.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductCursorResponseDto {
    private List<ProductDto> items;
    private Long nextCursor; // 마지막 아이템의 ID
    private boolean hasNext;
}