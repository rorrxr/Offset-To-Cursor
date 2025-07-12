package com.example.offsettocursor.product.repository;

import com.example.offsettocursor.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom  {

    /**
     * 커서 기반 페이지네이션 구현 방식 비교
     * 
     * 1. JPQL 방식 (작동하지 않음)
     * - JPQL은 JPA의 객체 지향 쿼리 언어입니다.
     * - 장점: 데이터베이스에 독립적, 객체 지향적 쿼리 작성
     * - 문제점: LIMIT 절을 직접 사용할 수 없음
     */
//    @Query("SELECT p FROM Product p ORDER BY p.id ASC LIMIT ?1")
//    List<Product> findTopN(int limit);
//
//    @Query("SELECT p FROM Product p WHERE p.id > ?1 ORDER BY p.id ASC LIMIT ?2")
//    List<Product> findNextN(Long lastId, int limit);

    /**
     * 2. Native Query 방식 (정상 작동)
     * - SQL을 직접 사용하는 방식입니다.
     * - 장점: 
     *   - LIMIT 절 사용 가능
     *   - 데이터베이스의 모든 기능 활용 가능
     * - 단점:
     *   - 특정 데이터베이스에 종속적
     *   - 데이터베이스가 변경되면 쿼리도 수정 필요
     */
//    @Query(value = "SELECT * FROM product WHERE id > ?1 ORDER BY id ASC LIMIT ?2", nativeQuery = true)
//    List<Product> findNextN(Long lastId, int limit);
//
//    @Query(value = "SELECT * FROM product ORDER BY id ASC LIMIT ?1", nativeQuery = true)
//    List<Product> findTopN(int limit);


    /**
     * 커서 기반 페이지네이션 - 첫 페이지 조회
     * Native Query를 사용하여 상품 목록의 첫 페이지를 조회합니다.
     * 
     * 특징:
     * - Native Query 사용: JPQL에서 LIMIT 절을 직접 사용할 수 없어 Native Query로 구현
     * - ID 기준 정렬: 정확한 페이지네이션을 위해 고유한 ID 값으로 정렬
     * - LIMIT 사용: 지정된 개수만큼만 데이터 조회
     *
     * @param limit 조회할 상품의 최대 개수
     * @return 상품 목록 (최대 limit 개수만큼 반환)
     */
    @Query(value = "SELECT * FROM product ORDER BY id ASC LIMIT ?1", nativeQuery = true)
    List<Product> findTopN(int limit);

    /**
     * 커서 기반 페이지네이션 - 다음 페이지 조회
     * Native Query를 사용하여 주어진 ID 이후의 상품들을 조회합니다.
     * 
     * 특징:
     * - Native Query 사용: JPQL의 LIMIT 제약 해결
     * - 커서 기반 필터링: lastId보다 큰 ID를 가진 상품들만 조회
     * - 순차적 접근: ID 기준 오름차순 정렬로 일관된 순서 보장
     * - 효율적인 쿼리: OFFSET 없이 ID 비교만으로 페이지네이션 구현
     *
     * 대체 구현 방법:
     * 1. JPQL + PageRequest: Pageable 객체를 사용한 구현
     * 2. QueryDSL: 타입 안전한 동적 쿼리 구현
     *
     * @param lastId 이전 페이지의 마지막 상품 ID
     * @param limit 조회할 상품의 최대 개수
     * @return 다음 페이지 상품 목록 (최대 limit 개수만큼 반환)
     */
    @Query(value = "SELECT * FROM product WHERE id > ?1 ORDER BY id ASC LIMIT ?2", nativeQuery = true)
    List<Product> findNextN(Long lastId, int limit);
}