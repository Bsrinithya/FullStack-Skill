package com.klu.repository;

import com.klu.model.Product;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByC(String c);

    List<Product> findByCAndPGreaterThan(String c, double p);

    List<Product> findByCOrN(String c, String n);

    List<Product> findByPBetween(double a, double b);

    List<Product> findByNLike(String k);

    List<Product> findByPGreaterThan(double p);

    long countByC(String c);

    boolean existsByN(String n);

    void deleteByN(String n);

    @Query("SELECT p FROM Product p ORDER BY p.p ASC")
    List<Product> sortByP();
}