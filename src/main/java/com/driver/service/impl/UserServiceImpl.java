package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity = new UserEntity();
       if( userRepository.findByEmail(user.getEmail()) != null){
           throw new Exception("User already present");
       }
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        String userId = String.valueOf(new SecureRandom());
        userEntity.setUserId( userId);
        userEntity = userRepository.save(userEntity); // save DB
        user.setUserId(userEntity.getUserId());
        return user;
    }

    @Override
    public UserDto getUser(String email) throws Exception {

        UserEntity userEntity = userRepository.findByEmail(email);
        if( userEntity == null) { // user not present
            throw new Exception("Email id invalid");
        }

        UserDto userDto = new UserDto();
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setUserId( userEntity.getUserId());
        userDto.setId(userEntity.getId());

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if( userEntity == null ) { // user not present
            throw new Exception("In valid Id!");
        }

        UserDto userDto = new UserDto();
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setUserId( userEntity.getUserId());
        userDto.setId(userEntity.getId());

        return userDto;

    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if( userEntity == null ) { // user not present
            throw new Exception(userId+" Id is invalid");
        }

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity = userRepository.save(userEntity);
        user.setId(userEntity.getId());
        return user;
    }

    @Override
    public void deleteUser(String userId) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if( userEntity == null ){ // user not found
            throw new Exception(userId+" not valid");
        }
        Long id =  userEntity.getId();
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsers() {

        Iterable<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for( UserEntity temp : userEntityList){
            UserDto userDto = new UserDto();
            userDto.setFirstName(temp.getFirstName());
            userDto.setLastName(temp.getLastName());
            userDto.setEmail(temp.getEmail());
            userDto.setId(temp.getId());
            userDto.setUserId(temp.getUserId());
            userDtoList.add(userDto);
        }

        return userDtoList;
    }
}