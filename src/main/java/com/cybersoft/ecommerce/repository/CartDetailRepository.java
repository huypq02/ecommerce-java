package com.cybersoft.ecommerce.repository;

import com.cybersoft.ecommerce.entity.CartDetailEntity;
import com.cybersoft.ecommerce.entity.CartEntity;
import com.cybersoft.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Integer> {
    Optional<CartDetailEntity> findByCartDetailAndCartProduct(CartEntity cart, ProductEntity product);
}
