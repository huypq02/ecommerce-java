package com.cybersoft.ecommerce.repository;

import com.cybersoft.ecommerce.entity.CartEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    Optional<CartEntity> findByUserInfoEntity(UserInfoEntity userInfoEntity);
}
