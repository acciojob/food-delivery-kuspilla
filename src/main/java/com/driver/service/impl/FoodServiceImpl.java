package com.driver.service.impl;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
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
            throw new Exception(foodId+" in valid Id");
        }

        BeanUtils.copyProperties(foodDetails, foodEntity);
        foodEntity = foodRepository.save(foodEntity);
        foodDetails.setId(foodEntity.getId());

        return foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        if(foodEntity == null ) {// invalid id
            throw new Exception(id + " is not valid");
        }
        Long foodId = foodEntity.getId();
        foodRepository.deleteById(foodId);
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