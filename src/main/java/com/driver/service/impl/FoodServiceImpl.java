package com.driver.service.impl;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;


@Service

public class FoodServiceImpl implements FoodService{

    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {

        FoodEntity foodEntity = new FoodEntity();
        String foodIdCreate = String.valueOf(new SecureRandom());
        food.setFoodId(foodIdCreate);
        // copy all objects user BeanUtils
        BeanUtils.copyProperties(food,foodEntity);
        foodEntity = foodRepository.save(foodEntity);
        food.setFoodId(foodEntity.getFoodId());

        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if( foodEntity == null){
            throw new Exception(foodId+" is invalid");
        }
        FoodDto foodDto = new FoodDto();
        BeanUtils.copyProperties(foodEntity,foodDto);

        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if(foodEntity == null ){ // not foodId present
            throw new Exception(foodId);
        }
        foodEntity.setFoodPrice(foodDetails.getFoodPrice());
        foodEntity.setFoodCategory(foodDetails.getFoodCategory());
        foodEntity.setFoodName(foodDetails.getFoodName());

        foodEntity = foodRepository.save(foodEntity);
        FoodDto foodDto = new FoodDto();
        ModelMapper modelMapper = new ModelMapper();
        foodDto = modelMapper.map(foodDto, FoodDto.class);

        return foodDto;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        if(foodEntity == null ) {// invalid id
            throw new Exception(id + " is not valid");
        }
        foodRepository.delete(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {
        Iterable<FoodEntity> foodDtoIterable = foodRepository.findAll();
        List<FoodDto> foodDtoList = new ArrayList<>();
        for( FoodEntity temp : foodDtoIterable){
            FoodDto foodDto = new FoodDto();
            BeanUtils.copyProperties(temp, foodDto);
            foodDtoList.add(foodDto);
        }

        return foodDtoList;
    }
}