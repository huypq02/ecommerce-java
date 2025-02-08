package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.ImageDto;
import com.cybersoft.ecommerce.dto.ProductDetailDto;
import com.cybersoft.ecommerce.dto.ProductDto;
import com.cybersoft.ecommerce.entity.*;
import com.cybersoft.ecommerce.exception.InsertException;
import com.cybersoft.ecommerce.repository.ImageRepository;
import com.cybersoft.ecommerce.repository.ProductDetailRepository;
import com.cybersoft.ecommerce.repository.ProductRepository;
import com.cybersoft.ecommerce.repository.VariantRepository;
import com.cybersoft.ecommerce.request.ImageRequest;
import com.cybersoft.ecommerce.request.InsertProductionRequest;
import com.cybersoft.ecommerce.request.ProductDetailRequest;
import com.cybersoft.ecommerce.request.ProductRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private VariantRepository variantRepository;

//    @Override
//    public void insertProduct(ProductRequest productRequest, MultipartFile[] files) {
//        try {
//            // 1. Thêm sản phẩm
//            ProductEntity product = new ProductEntity();
//            product.setName(productRequest.getName());
//            product.setNote(productRequest.getNote());
//            product.setRate(productRequest.getRate() != null ? productRequest.getRate() : 5.0);
//
//            // 2. Thêm các biến thể sản phẩm
//            for (ProductDetailRequest detailRequest : productRequest.getProductDetailRequests()) {
//                ProductDetailEntity productDetail = new ProductDetailEntity();
//                productDetail.setProductEntity(product);
//                productDetail.setPrice(detailRequest.getPrice());
//                productDetail.setSize(detailRequest.getSize());
//                productDetail.setColor(detailRequest.getColor());
//                productDetail.setQuantity_stock(detailRequest.getQuantityStock());
//                productDetailRepository.save(productDetail);
//
//
//                // 3. Thêm danh sách hình ảnh của từng biến thể
//                for (ImageRequest imageRequest : detailRequest.getImages()) {
//                    ImageEntity image = new ImageEntity();
//                    image.setProductDetailEntity(productDetail);
//                    image.setUrlName(imageRequest.getUrl());
//                    image.setAll_text(imageRequest.getAllText());
//                    imageRepository.save(image);
//                }
//            }
//
//            // 4. Lưu thông tin sản phẩm vào CSDL
//            productRepository.save(product);
//        } catch (Exception e) {
//            throw new InsertException("[insertProduct] Không thể thêm sản phẩm" + e);
//        }
//    }

    @Override
    public void insertProduct(ProductRequest productRequest, MultipartFile[] files) {

        try {
            // 1. Thêm sản phẩm
            ProductEntity product = new ProductEntity();
            product.setName(productRequest.getName());
            product.setNote(productRequest.getNote());
            if(productRequest.getRate() == null){
                product.setRate(5);
            } else {
                product.setRate(productRequest.getRate());
            }
            //productRepository.save(product);
            // 2. Thêm các biến thể sản phẩm
            int fileIndex = 0;
            for(ProductDetailRequest detailRequest : productRequest.getProductDetailRequests()){
                ProductDetailEntity productDetail = new ProductDetailEntity();
                productDetail.setProductEntity(product);
                productDetail.setPrice(detailRequest.getPrice());
                productDetail.setSize(detailRequest.getSize());
                productDetail.setColor(detailRequest.getColor());
                productDetail.setQuantity_stock(detailRequest.getQuantityStock());
                productDetail.setProductEntity(product);
                productDetailRepository.save(productDetail);
                // 3. Thêm danh sách hình ảnh của từng biến thể
                for (ImageRequest imageRequest : detailRequest.getImages()){
                    if (fileIndex < files.length){
                        MultipartFile file = files[fileIndex];
                        String fileName = fileService.uploadFile(file);

                        ImageEntity image = new ImageEntity();
                        image.setProductDetailEntity(productDetail);
                        image.setUrlName(fileName);
                        image.setAll_text(imageRequest.getAllText());
                        //productDetail.getImageEntityList().add(image);
                        imageRepository.save(image);
                        fileIndex++;  // Chuyển sang ảnh tiếp theo

                    }
                    //product.getDetailEntityList().add(productDetail);
                }
                productRepository.save(product);
            }


        } catch (Exception e) {
            throw new InsertException("[insertProduct] Cannot insert product");
        }
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
