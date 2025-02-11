package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.CartDetailEntity;
import com.cybersoft.ecommerce.entity.CartEntity;
import com.cybersoft.ecommerce.entity.ProductEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import com.cybersoft.ecommerce.repository.*;
import com.cybersoft.ecommerce.request.CartRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Override


@Transactional
    public String addToCart(CartRequest cartRequest) {

// Tìm userInfoEntity theo ID
        Optional<UserInfoEntity> userInfoEntityOpt = userInfoRepository.findById(cartRequest.getUserInfo().getId());

// Nếu user không tồn tại, trả về lỗi
        if (!userInfoEntityOpt.isPresent()) {
            return "User not found!";
        }

// Lấy `UserInfoEntity` từ Optional
        UserInfoEntity userInfoEntity = userInfoEntityOpt.get();

// Tìm cart theo userInfoEntity, nếu không có thì tạo mới
        CartEntity cart = cartRepository.findByUserInfoEntity(userInfoEntity)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUserInfoEntity(userInfoEntity); // Gán user vào cart
                    return cartRepository.save(newCart); // Lưu cart mới vào DB
                });

        // Tìm product theo ID
        Optional<ProductEntity> productOpt = productRepository.findById(cartRequest.getProductID());
        if (!productOpt.isPresent()) {
            return "Product not found!";
        }

        ProductEntity product = productOpt.get();

        // Tìm xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartDetailEntity> cartDetailOpt = cartDetailRepository.findByCartDetailAndCartProduct(cart, product);

        if (cartDetailOpt.isPresent()) {
            // Nếu đã có, cập nhật số lượng
            CartDetailEntity cartDetail = cartDetailOpt.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + cartRequest.getQuantity());
            cartDetailRepository.save(cartDetail);
        } else {
            // Nếu chưa có, thêm mới vào giỏ hàng
            CartDetailEntity newCartDetail = new CartDetailEntity();
            newCartDetail.setCartDetail(cart);
            newCartDetail.setCartProduct(product);
            newCartDetail.setQuantity(cartRequest.getQuantity());

            cartDetailRepository.save(newCartDetail);
        }

        return "Product added to cart successfully!";
    }


}
