package com.example.offsettocursor.product.service;

import com.example.offsettocursor.product.dto.ProductCursorResponseDto;
import com.example.offsettocursor.product.dto.ProductDto;
import com.example.offsettocursor.product.entity.Product;
import com.example.offsettocursor.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * <오프셋 기반> 페이지네이션을 사용하여 상품 목록을 조회합니다.
     * 상품 목록을 페이지네이션하여 조회하고 ProductDto 객체로 변환합니다.
     * 상품들은 ID를 기준으로 오름차순 정렬됩니다.
     *
     * @param page 조회할 페이지 번호 (0부터 시작)
     * @param size 한 페이지당 조회할 상품의 개수
     * @return 지정된 페이지와 크기에 해당하는 ProductDto 객체들을 포함하는 Page 객체
     */

    // Offset 기반
    public Page<ProductDto> getPagedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return productRepository.findAll(pageable)
                .map(ProductDto::from);
    }

    /**
     * <커서 기반> 페이지네이션을 사용하여 상품 목록을 조회합니다.
     * 첫 요청 시에는 lastId가 null이며, 이후 요청에서는 마지막으로 받은 상품의 ID를 lastId로 사용합니다.
     *
     * @param lastId 이전 페이지의 마지막 상품 ID (첫 요청시 null)
     * @param size 조회할 상품의 개수
     * @return ProductCursorResponse 객체 (상품 목록, 다음 커서 값, 다음 페이지 존재 여부 포함)
     */
    // Cursor 기반
    public ProductCursorResponseDto getProductsByCursor(Long lastId, int size) {
        List<Product> products;

        if (lastId == null) {
            // 첫 요청: 가장 작은 ID부터
            products = productRepository.findTopN(size + 1);
        } else {
            // 커서 이후의 상품
            products = productRepository.findNextN(lastId, size + 1);
        }

        boolean hasNext = products.size() > size;
        if (hasNext) {
            products = products.subList(0, size);
        }

        List<ProductDto> items = products.stream()
                .map(ProductDto::from)
                .toList();

        Long nextCursor = hasNext ? items.get(items.size() - 1).getId() : null;

        return ProductCursorResponseDto.builder()
                .items(items)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

}
