package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.request.ProductRequest;
import com.cybersoft.ecommerce.request.TryObject;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    // Da chay okie
//
//    @PostMapping("/add", consumes = "multipart/form-data")
//    public BaseResponse insertProduct(
//            @RequestPart("file") MultipartFile file,
//            @RequestPart("string") String string) {
//
//        System.out.println("Received file: " + file.getOriginalFilename());
//        System.out.println("Received string: " + string);
//
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setCode(200);
//        baseResponse.setMessage("Success");
//        baseResponse.setData("File uploaded successfully with string: " + string);
//        return baseResponse;
//    }

    @PostMapping(value = "/add")
    public BaseResponse insertProduct(@RequestPart String productRequestJson,@RequestPart MultipartFile[] files) {
        BaseResponse baseResponse = new BaseResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest productRequest;
        try {
            // Chuyển chuỗi JSON thành đối tượng productRequestJson
            productRequest = objectMapper.readValue(productRequestJson, ProductRequest.class);
            productService.insertProduct(productRequest,files);
            baseResponse.setCode(200);
            baseResponse.setMessage("Success");
            baseResponse.setData("Insert Data Success");

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi chuyển đổi JSON thành đối tượng productRequestJson", e);
        }

        return baseResponse;
    }

    @GetMapping()
    public BaseResponse getAllProduct(@RequestParam int pageSize, @RequestParam int pageNumber){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Success");
        baseResponse.setData(productService.getAllProduct(pageSize,pageNumber));
        return baseResponse;
    }
}
