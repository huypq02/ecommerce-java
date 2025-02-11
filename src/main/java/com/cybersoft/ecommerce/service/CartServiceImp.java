package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.CartDetailEntity;
import com.cybersoft.ecommerce.entity.CartEntity;
import com.cybersoft.ecommerce.entity.ProductEntity;
import com.cybersoft.ecommerce.repository.*;
import com.cybersoft.ecommerce.request.CartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {
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
    public String addToCart(CartRequest cartRequest) {
        // Tìm CartEntity và ProductEntity theo ID
        Optional<CartEntity> cartOpt = cartRepository.findById(cartRequest.getCartID());
        Optional<ProductEntity> productOpt = productRepository.findById(cartRequest.getProductID());

        // Kiểm tra xem cart và product có tồn tại không
        if (cartOpt.isPresent() && productOpt.isPresent()) {
            CartEntity cart = cartOpt.get();
            ProductEntity product = productOpt.get();

            // Tìm xem sản phẩm đã có trong giỏ hàng chưa
            Optional<CartDetailEntity> cartDetailOpt = cartDetailRepository.findByCartDetailAndCartProduct(cart, product);

            if (cartDetailOpt.isPresent()) {
                // Nếu có rồi, cập nhật số lượng
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

        return "Cart or Product not found!";
    }

}
