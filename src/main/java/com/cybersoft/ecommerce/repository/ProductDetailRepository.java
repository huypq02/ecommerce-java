package com.cybersoft.ecommerce.repository;

import com.cybersoft.ecommerce.entity.ProductDetailEntity;
import com.cybersoft.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetailEntity,Integer> {
}
