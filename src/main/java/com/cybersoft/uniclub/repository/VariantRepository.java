package com.cybersoft.uniclub.repository;

import com.cybersoft.uniclub.entity.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<VariantEntity, Integer> {
}
