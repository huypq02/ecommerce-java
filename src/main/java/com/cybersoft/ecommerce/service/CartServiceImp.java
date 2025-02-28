package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.CartDTO;
import com.cybersoft.ecommerce.entity.*;
import com.cybersoft.ecommerce.repository.*;
import com.cybersoft.ecommerce.request.CartRequest;
import com.cybersoft.ecommerce.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    @Transactional
    public void addToCart(CartRequest cartRequest) {
        if (cartRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0.");
        }
        // Step 1: Get user info from token by parsing JWT
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        Claims claims = jwtHelper.getClaims(token);
        int userId = claims.get("userId", Integer.class);
        // Nếu giỏ hàng chưa tồn tại, tạo giỏ hàng mới
        Optional<CartEntity> cartOptional = cartRepository.findCartByUserID(userRepository.findById(userId).get());
        CartEntity cart = cartOptional.orElseGet(() -> createCart(userId));

        // Kiểm tra sản phẩm có trong giỏ hàng chưa
        ProductDetailEntity productDetail = productDetailRepository.findById(cartRequest.getProductDetailID())
                .orElseThrow(() -> new RuntimeException("Product not found!"));


        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartDetailEntity> existingCartDetail = cartDetailRepository.findByCartDetailIDAndCartProductDetailID(cart, productDetail);

        if (existingCartDetail.isPresent()) {
            // Nếu sản phẩm đã có, cập nhật số lượng
            CartDetailEntity cartDetail = existingCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + cartRequest.getQuantity());
            cartDetailRepository.save(cartDetail);
        } else {
            // Nếu chưa có, thêm mới sản phẩm vào giỏ hàng
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(cartRequest.getProductDetailID());

            CartDetailEntity cartDetail = new CartDetailEntity();
            cartDetail.setCartDetailID(cart);
            cartDetail.setCartProductDetailID(productDetail);
            cartDetail.setQuantity(cartRequest.getQuantity());
            cartDetailRepository.save(cartDetail);
        }
    }

    private CartEntity createCart(int userID) {
        CartEntity cart = new CartEntity();

        cart.setUserID(userRepository.findById(userID).get());
        return cartRepository.save(cart);
    }

    @Override
    public List<CartDTO> getAllCarts() {
        // Step 1: Get user info from token by parsing JWT
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        Claims claims = jwtHelper.getClaims(token);
        int userId = claims.get("userId", Integer.class);

        CartEntity cart = cartRepository.findCartByUserID(userRepository.findById(userId).get()).orElseThrow(() -> new RuntimeException("Cart not found!"));
        List<CartDetailEntity> cartDetails = cartDetailRepository.findByCartDetailID(cart);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (CartDetailEntity cartDetailEntity : cartDetails) {
            ProductEntity product = cartDetailEntity.getCartProductDetailID().getProductEntity();
            List<ProductDetailEntity> productDetails = product.getDetailEntityList();
            if (!productDetails.isEmpty()) {
                ProductDetailEntity productDetail = productDetails.get(0); // Lấy product detail đầu tiên
                CartDTO cartDTO = new CartDTO();
                cartDTO.setProductId(product.getId());
                cartDTO.setProductName(product.getName());
                cartDTO.setQuantity(cartDetailEntity.getQuantity());
                cartDTO.setSize(productDetail.getSize());
                cartDTO.setPrice(productDetail.getPrice());
                cartDTO.setColor(productDetail.getColor());
                cartDTO.setProductDetailId(productDetail.getId());
                List<String> imageUrls = productDetail.getImageEntityList()// Lấy danh sách ảnh từ ProductDetailEntity
                        .stream()
                        .map(imageEntity -> "http://localhost:8080/download/" + imageEntity.getUrlName())// Lấy URL của ảnh
                        .collect(Collectors.toList());
                cartDTO.setImageUrls(imageUrls); // Gán danh sách ảnh vào DTO

                cartDTOList.add(cartDTO);
            }

        }
        return cartDTOList;
    }

    @Override
    @Transactional
    public void deleteCart(int productDetailId) {
        // Step 1: Get user info from token by parsing JWT
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        Claims claims = jwtHelper.getClaims(token);
        int userId = claims.get("userId", Integer.class);

        CartEntity cart = cartRepository.findCartByUserID(userRepository.findById(userId).get()).orElseThrow(() -> new RuntimeException("Cart not found!"));
        ProductDetailEntity productDetail = productDetailRepository.findById(productDetailId).orElseThrow(() -> new RuntimeException("Product detail not found!"));
        CartDetailEntity cartDetail = cartDetailRepository.findByCartDetailIDAndCartProductDetailID(cart, productDetail).orElseThrow(() -> new RuntimeException("Cart detail not found!"));
        cartDetailRepository.delete(cartDetail);
    }

    @Override
    @Transactional
    public boolean deleteCartByUserId() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7).trim();

        if (token.isEmpty() || token.contains(" ")) {
            throw new RuntimeException("Invalid JWT token format");
        }

        Claims claims = jwtHelper.getClaims(token);
        int userId = claims.get("userId", Integer.class);

        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found!");
        }

        UserEntity user = userOptional.get();
        Optional<CartEntity> cartOptional = cartRepository.findCartByUserID(user);

        if (!cartOptional.isPresent()) {
            throw new RuntimeException("Cart not found!");
        }

        cartRepository.deleteByUserID(user);
        return true;
    }
}
