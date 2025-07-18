package com.ecommerce.user.service;


import com.ecommerce.user.dto.AddressResDto;
import com.ecommerce.user.dto.UserReqDtos;
import com.ecommerce.user.dto.UserResDto;
import com.ecommerce.user.entity.Address;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;

    private UserResDto mapUsersToUserResDto(User user) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(user.getId());
        userResDto.setEmail(user.getEmail());
        userResDto.setName(user.getName());
        userResDto.setUsername(user.getUsername());

        userResDto.setAddress(mapAddress(user.getAddress()));
        return userResDto;
    }

    // Convert Address Entity to Response DTO
    private AddressResDto mapAddress(Address address) {
        if (address == null) return null;
        AddressResDto dto = new AddressResDto();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setZip(address.getZip());
        dto.setState(address.getState() != null ? address.getState() : "");
        return dto;
    }

    public List<UserResDto> getAllUsers(){
        List<User> users = userRepo.findAll();
        return users.stream().map(this::mapUsersToUserResDto).toList();
    }

    public void createUser(UserReqDtos user){
        User user1 = new User();

        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setName(user.getName());
        Address address = new Address();
        address.setCity(user.getAddress().getCity());
        address.setCountry(user.getAddress().getCountry());
        address.setStreet(user.getAddress().getStreet());
        address.setZip(user.getAddress().getZip());

        user1.setAddress(address);
        log.info("user created successfully");

        userRepo.save(user1);
    }

    public UserResDto getUserById(String id) {
        return userRepo.findById(id).map(this::mapUsersToUserResDto).orElse(null);
    }

    public boolean updateUserById(String id, UserReqDtos updatingUser) {
        return userRepo.findById(id)
                .map(user ->{
                    updateUserReq(user,updatingUser);
                    userRepo.save(user);
                    return true;
                }).orElse(false);
    }

    private void updateUserReq(User user,UserReqDtos userReqDtos){
        user.setEmail(userReqDtos.getEmail());
        user.setName(userReqDtos.getName());
        user.setUsername(userReqDtos.getUsername());
        if (userReqDtos.getAddress() != null) {
            user.getAddress().setStreet(userReqDtos.getAddress().getStreet());
            user.getAddress().setCity(userReqDtos.getAddress().getCity());
            user.getAddress().setCountry(userReqDtos.getAddress().getCountry());
            user.getAddress().setZip(userReqDtos.getAddress().getZip());

        }
    }
}
