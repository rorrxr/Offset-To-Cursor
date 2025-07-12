package com.example.offsettocursor.product.repository;

import com.example.offsettocursor.product.entity.Product;
import com.example.offsettocursor.product.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    /**
     * QueryDSL을 위한 Q타입 엔티티입니다.
     * QProduct는 Product 엔티티의 메타모델로, 타입 안전한 쿼리 작성을 가능하게 합니다.
     */
    QProduct product = QProduct.product;

    /**
     * QueryDSL을 사용하여 커서 기반 페이지네이션을 구현한 상품 조회 메서드입니다.
     * 
     * 동작 방식:
     * 1. lastId가 null인 경우: 처음부터 size+1개의 상품을 조회합니다.
     * 2. lastId가 있는 경우: lastId보다 큰 ID를 가진 상품들 중 size+1개를 조회합니다.
     * 3. size+1개를 조회하는 이유는 다음 페이지 존재 여부를 확인하기 위함입니다.
     *
     * 쿼리 구성:
     * - selectFrom(product): Product 테이블에서 전체 컬럼 선택
     * - where절: 동적으로 lastId 조건 적용 (null이면 조건 미적용)
     * - orderBy: ID 기준 오름차순 정렬
     * - limit: size + 1 (다음 페이지 존재 여부 확인용)
     *
     * @param lastId 이전 페이지의 마지막 상품 ID (첫 페이지 요청시 null)
     * @param size 조회할 상품의 개수
     * @return 조회된 상품 리스트 (size + 1 개까지 반환 가능)
     */
    @Override
    public List<Product> findProductsByCursorQuery(Long lastId, int size) {
        return queryFactory
                .selectFrom(product)
                .where(lastId != null ? product.id.gt(lastId) : null)
                .orderBy(product.id.asc())
                .limit(size + 1)
                .fetch();
    }
}