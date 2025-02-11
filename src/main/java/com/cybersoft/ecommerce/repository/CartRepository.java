package com.cybersoft.ecommerce.repository;

import com.cybersoft.ecommerce.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
}
