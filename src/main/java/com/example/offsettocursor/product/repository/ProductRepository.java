package com.example.offsettocursor.product.repository;

import com.example.offsettocursor.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 커서 기반
    @Query("SELECT p FROM Product p ORDER BY p.id ASC LIMIT ?1")
    List<Product> findTopN(int limit);

    @Query("SELECT p FROM Product p WHERE p.id > ?1 ORDER BY p.id ASC LIMIT ?2")
    List<Product> findNextN(Long lastId, int limit);

    /**
    * JPQL에서 LIMIT은 직접 안 되기 때문에 Native Query 또는
     * PageRequest with filter 방식도 가능합니다.
     * 아래는 Native로 바꾸는 커서 기반 예시입니다.
    * */

    // @Query(value = "SELECT * FROM product WHERE id > ?1 ORDER BY id ASC LIMIT ?2", nativeQuery = true)
    // List<Product> findNextN(Long lastId, int limit);

    // @Query(value = "SELECT * FROM product ORDER BY id ASC LIMIT ?1", nativeQuery = true)
    // List<Product> findTopN(int limit);

}
