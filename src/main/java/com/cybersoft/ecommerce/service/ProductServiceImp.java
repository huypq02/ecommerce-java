package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.ProductDto;
import com.cybersoft.ecommerce.entity.*;
import com.cybersoft.ecommerce.exception.InsertException;
import com.cybersoft.ecommerce.repository.ProductRepository;
import com.cybersoft.ecommerce.repository.VariantRepository;
import com.cybersoft.ecommerce.request.InsertProductionRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private FileService fileService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Override
    public void insertProduct(InsertProductionRequest file) {
        try {
            fileService.uploadFile(file);

            ProductEntity product = new ProductEntity();
            product.setName(file.getName());

            BrandEntity brand = new BrandEntity();
            brand.setId(file.getBrandId());

            product = productRepository.save(product);

            VariantEntity variant = new VariantEntity();
            variant.setProduct(product);

            ColorEntity color = new ColorEntity();
            color.setId(file.getColorId());
            variant.setColor(color);

            SizeEntity size = new SizeEntity();
            size.setId(file.getSizeId());
            variant.setSize(size);

            variant.setImages(file.getFile().getOriginalFilename());
            variant.setQuantity(file.getQuantity());
            variant.setPrice(file.getPrice());
            variantRepository.save(variant);

        } catch (Exception e) {
            throw new InsertException("[insertProduct] Cannot insert product");
        }
    }

    @Override
    public List<ProductDto> getAllProduct(int pageSize, int pageNumber) {

        Pageable page = PageRequest.of(pageNumber, pageSize);

        return productRepository.findAll(page).stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setNote(product.getNote());
            productDto.setRate(product.getRate());

            return productDto;
        }).toList();
    }
}
