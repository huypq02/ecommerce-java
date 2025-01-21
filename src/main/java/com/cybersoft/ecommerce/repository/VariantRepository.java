package com.cybersoft.ecommerce.repository;

import com.cybersoft.ecommerce.entity.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<VariantEntity, Integer> {
}
