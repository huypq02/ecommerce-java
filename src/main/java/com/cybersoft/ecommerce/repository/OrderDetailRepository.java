package com.cybersoft.ecommerce.repository;

import com.cybersoft.ecommerce.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
}
