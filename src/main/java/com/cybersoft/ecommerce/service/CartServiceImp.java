package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.CartDTO;
import com.cybersoft.ecommerce.entity.*;
import com.cybersoft.ecommerce.repository.*;
import com.cybersoft.ecommerce.request.CartRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void addToCart(CartRequest cartRequest) {
        if (cartRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng sản phẩm phải lớn hơn 0.");
        }
        Optional<CartEntity> cartOptional = cartRepository.findById(cartRequest.getCartID());
        // Nếu giỏ hàng chưa tồn tại, tạo giỏ hàng mới
        CartEntity cart = cartOptional.orElseGet(() -> createCart(cartRequest.getUserID()));

        // Kiểm tra sản phẩm có trong giỏ hàng chưa
        ProductEntity product = productRepository.findById(cartRequest.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found!"));


        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartDetailEntity> existingCartDetail = cartDetailRepository.findByCartDetailIDAndCartProductID(cart, product);

        if (existingCartDetail.isPresent()) {
            // Nếu sản phẩm đã có, cập nhật số lượng
            CartDetailEntity cartDetail = existingCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + cartRequest.getQuantity());
            cartDetailRepository.save(cartDetail);
        } else {
            // Nếu chưa có, thêm mới sản phẩm vào giỏ hàng
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(cartRequest.getProductID());

            CartDetailEntity cartDetail = new CartDetailEntity();
            cartDetail.setCartDetailID(cart);
            cartDetail.setCartProductID(product);
            cartDetail.setQuantity(cartRequest.getQuantity());
            cartDetailRepository.save(cartDetail);
        }
    }

    private CartEntity createCart(int userID) {
        CartEntity cart = new CartEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userID);

        cart.setUserID(userEntity);
        return cartRepository.save(cart);
    }

    @Override
    public List<CartDTO> getAllCarts(CartRequest cartRequest) {
        CartEntity cart = cartRepository.findById(cartRequest.getCartID()) .orElseThrow(() -> new RuntimeException("Cart not found!"));
        List<CartDetailEntity> cartDetails = cartDetailRepository.findByCartDetailID(cart);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (CartDetailEntity cartDetailEntity : cartDetails) {
            ProductEntity product = cartDetailEntity.getCartProductID();
            List<ProductDetailEntity> productDetails = product.getDetailEntityList();
            if(!productDetails.isEmpty()){
                ProductDetailEntity productDetail = productDetails.get(0); // Lấy product detail đầu tiên
                CartDTO cartDTO = new CartDTO();
                cartDTO.setProductName(product.getName());
                cartDTO.setQuantity(cartDetailEntity.getQuantity());
                cartDTO.setSize(productDetail.getSize());
                cartDTO.setPrice(productDetail.getPrice());
                List<String> imageUrls = productDetail.getImageEntityList()// Lấy danh sách ảnh từ ProductDetailEntity
                        .stream()
                        .map(ImageEntity::getUrlName)// Lấy URL của ảnh
                        .collect(Collectors.toList());
                cartDTO.setImageUrls(imageUrls); // Gán danh sách ảnh vào DTO

                cartDTOList.add(cartDTO);
            }

        }
        return cartDTOList;
    }
}
