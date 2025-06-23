package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.RoleDto;
import com.cybersoft.ecommerce.dto.UserDto;
import com.cybersoft.ecommerce.dto.UserInfoDto;
import com.cybersoft.ecommerce.entity.RoleEntity;
import com.cybersoft.ecommerce.entity.UserEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import com.cybersoft.ecommerce.repository.RoleRepository;
import com.cybersoft.ecommerce.repository.UserInfoRepository;
import com.cybersoft.ecommerce.repository.UserRepository;
import com.cybersoft.ecommerce.request.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUser() {
        try {
            return userRepository.findAll().stream().map(
                    userEntity -> {
                        UserDto userDto = new UserDto();
                        userDto.setId(userEntity.getId());
                        userDto.setEmail(userEntity.getEmail());
                        userDto.setRole(userEntity.getRole());
                        userDto.setOauthId(userEntity.getOauthId());

                        if (userEntity.getUserInfo() != null) {
                            UserInfoEntity userInfoEntity = userInfoRepository.findById(userEntity.getUserInfo().getId()).orElse(null);
                            if (userInfoEntity != null) {
                                UserInfoDto userInfoDto = new UserInfoDto();
                                userInfoDto.setId(userInfoEntity.getId());
                                userInfoDto.setFullName(userInfoEntity.getFullName());
                                userInfoDto.setGender(userInfoEntity.getGender());
                                userInfoDto.setBirthday(userInfoEntity.getBirthday());
                                userInfoDto.setAddress(userInfoEntity.getAddress());
                                userInfoDto.setPhone(userInfoEntity.getPhone());
                                userInfoDto.setDescription(userInfoEntity.getDescription());
                                userDto.setUserInfo(userInfoDto);
                            }
                        }

                        RoleEntity roleEntity = roleRepository.findByRole(userEntity.getRole().getRole())
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        RoleDto roleDto = new RoleDto();
                        roleDto.setId(roleEntity.getId());
                        roleDto.setRole(roleEntity.getRole());
                        roleDto.setName(roleEntity.getName());

                        return userDto;
                    }
            ).toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUser(UserRequest userRequest) {
        try {
            UserEntity userEntity = userRepository.findById(userRequest.getId()).get();
            if (userRequest.getEmail() != null){
                userEntity.setEmail(userRequest.getEmail());
            }
            if (userRequest.getPassword() != null){
                String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
                userEntity.setPassword(encodedPassword);
            }
            userEntity.setRole(roleRepository.findByRole(userRequest.getRole()).get()); // Set role

            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        try {
            UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            if (userEntity.getUserInfo() != null) {
                userInfoRepository.deleteById(userEntity.getUserInfo().getId());
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete user failed: " + e.getMessage());
        }
    }
}
