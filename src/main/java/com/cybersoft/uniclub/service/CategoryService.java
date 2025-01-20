package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
}
