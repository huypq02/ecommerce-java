package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.ImageEntity;
import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import com.cybersoft.ecommerce.repository.ImageRepository;
import com.cybersoft.ecommerce.repository.RoleRepository;
import com.cybersoft.ecommerce.repository.UserInfoRepository;
import com.cybersoft.ecommerce.repository.UserRepository;
import com.cybersoft.ecommerce.request.AccountRequest;
import com.cybersoft.ecommerce.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private ImageRepository imageRepository;

    private static final int USER_ROLE_ID = 2;

    @Override
    public UserEntity getUserByAuthorToken() {
        UserEntity userEntity = null;

        //1. Get userId
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        Claims claims = jwtHelper.getClaims(token);
        int userId = claims.get("userId", Integer.class);

        //2. Get user-info
        Optional<UserEntity> existUser = userRepository.findById(userId);
        if (existUser.isPresent()) {
            userEntity = existUser.get();
            UserInfoEntity userInfo = userEntity.getUserInfo();
            if (userInfo != null) {
                String birth = userInfo.getBirthday();
                if (birth != null && birth.contains(" ")) {
                    birth = birth.split(" ")[0];
                    userInfo.setBirthday(birth);
                    userEntity.setUserInfo(userInfo);
                }
            }
        } else {
            System.out.println("User with id: " + userId + " isnot exist!");
        }

        return userEntity;
    }

    @Override
    public boolean updateUser(AccountRequest accountRequest) {
        boolean result = false;

        //Get userId
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        Claims claims = jwtHelper.getClaims(token);
        int userId = claims.get("userId", Integer.class);

        try {
            if (userId <= 0 || accountRequest.getEmail() == null
                    || accountRequest.getEmail().equals("")) {
                System.out.println("AccountServiceImpl updateUser accountRequest is invalid!");
                return result;
            }

            //Save image
            MultipartFile file = accountRequest.getImage();
            String fileName = "";
            if (file != null && !file.isEmpty()) {
                fileName = fileService.uploadFile(file);
            }

            //Save user-info
            Optional<UserEntity> existUser = userRepository.findById(userId);
            UserEntity userEntity = existUser.get();
            UserInfoEntity userInfo = userEntity.getUserInfo();
            if (userInfo == null) {
                userInfo = new UserInfoEntity();
            }
            userInfo.setFullName(accountRequest.getFullName());
            userInfo.setGender(accountRequest.getGender());
            userInfo.setBirthday(accountRequest.getBirthDay());
            userInfo.setAddress(accountRequest.getAddress());
            userInfo.setPhone(accountRequest.getPhone());
            userInfo.setDescription(accountRequest.getDescription());
            if (!fileName.equals("")) {
                userInfo.setImage(fileName);
            }

            UserInfoEntity savedUserInfo = userInfoRepository.save(userInfo);

            //Save user
            userEntity.setUserInfo(savedUserInfo);
            userEntity.setRole(roleRepository.findById(USER_ROLE_ID).get());
            userEntity.setEmail(accountRequest.getEmail());
            userEntity.setPassword(accountRequest.getPassword());

            userRepository.save(userEntity);
            result = true;
        } catch (Exception ex) {
            System.out.println("AccountServiceImpl updateUser error: " + ex.getMessage());
            ex.printStackTrace();
        }

        return result;
    }
}
