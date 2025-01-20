package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.dto.CategoryDto;
import com.cybersoft.uniclub.entity.CategoryEntity;
import com.cybersoft.uniclub.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<CategoryDto> getAllCategories() {
        try {
            List<CategoryDto> categoryDtos;
            if (redisTemplate.hasKey("categories")) {
                categoryDtos = (List<CategoryDto>) redisTemplate.opsForValue().get("categories");
                return categoryDtos;
            }
            categoryDtos =
                    categoryRepository.findAll().
                            stream().map(CategoryEntity::mapToDto).
                            toList();
            redisTemplate.opsForValue().set("categories", categoryDtos);
            return categoryDtos;
        } catch (Exception e) {
            throw  new RuntimeException("Cannot get categories");
        }
    }
}
