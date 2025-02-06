package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.ImageDto;
import com.cybersoft.ecommerce.dto.ProductDetailDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
//        try {
//            fileService.uploadFile(file);
//
//            ProductEntity product = new ProductEntity();
//            product.setName(file.getName());
//            product.setPrice(file.getPrice());
//
//            BrandEntity brand = new BrandEntity();
//            brand.setId(file.getBrandId());
//            product.setBrand(brand);
//
//            product = productRepository.save(product);
//
//            VariantEntity variant = new VariantEntity();
//            variant.setProduct(product);
//
//            ColorEntity color = new ColorEntity();
//            color.setId(file.getColorId());
//            variant.setColor(color);
//
//            SizeEntity size = new SizeEntity();
//            size.setId(file.getSizeId());
//            variant.setSize(size);
//
//            variant.setImages(file.getFile().getOriginalFilename());
//            variant.setQuantity(file.getQuantity());
//            variant.setPrice(file.getPrice());
//            variantRepository.save(variant);
//
//        } catch (Exception e) {
//            throw new InsertException("[insertProduct] Cannot insert product");
//        }
    }

    @Override
    public List<ProductDto> getAllProduct(int pageNumber, int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);

        return productRepository.findAll(page).stream().map(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setNote(product.getNote());
            productDto.setName(product.getName());
            productDto.setRate(product.getRate());

            List<ProductDetailDto> productDetailDtoList = product.getDetailEntityList().stream().map(
                    detail ->{
                        ProductDetailDto productDetailDto = new ProductDetailDto();
                        productDetailDto.setId(detail.getId());
                        productDetailDto.setColor(detail.getColor());
                        productDetailDto.setSize(detail.getSize());
                        productDetailDto.setQuantity(detail.getQuantity_stock());
                        productDetailDto.setPrice(detail.getPrice());
                        if (!detail.getImageEntityList().isEmpty()) {
                            //Xet truong hop co anh tranh loi
                            List<ImageDto> imageDtoList = detail.getImageEntityList().stream().map(
                                    image ->{
                                        ImageDto imageDto = new ImageDto();
                                        imageDto.setId(image.getId());
                                        imageDto.setUrlName("http://localhost:8080/download/"+image.getUrlName());
                                        imageDto.setAllText(image.getAll_text());
                                        return imageDto;
                                    }).toList();
                            productDetailDto.setImageDtoList(imageDtoList);
                        } else {
                            //Xet truong hop khong co anh
                            List<ImageDto> imageDtoList = new ArrayList<>();
                            productDetailDto.setImageDtoList(imageDtoList);
                        }
                        return productDetailDto;
                    }).toList();
            productDto.setProductDetailDtoList(productDetailDtoList);
            return productDto;
        }).toList();
    }
}
