package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodService foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{

		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			FoodDto foodDto = foodService.getFoodById(id);
		BeanUtils.copyProperties(foodDto, foodDetailsResponse);

		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {

		FoodDto foodDto = new FoodDto();
		BeanUtils.copyProperties(foodDetails,foodDto);
		foodDto = foodService.createFood(foodDto);
		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
		BeanUtils.copyProperties(foodDto,foodDetailsResponse);
		return foodDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{

		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
		FoodDto foodDto = new FoodDto();
		BeanUtils.copyProperties(foodDetails,foodDto);
		foodDto = foodService.updateFoodDetails(id, foodDto);
		BeanUtils.copyProperties(foodDto, foodDetailsResponse);
		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		try{
			foodService.deleteFoodItem(id);
			operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
			return operationStatusModel;
		}
		catch (Exception e){
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
		  return operationStatusModel;
		}

	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
          List<FoodDetailsResponse> foodDetailsResponsesList = new ArrayList<>();
		  Iterable<FoodDto> foodDtoIterable = foodService.getFoods();
		  for( FoodDto temp : foodDtoIterable){
			  FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			  BeanUtils.copyProperties(temp,foodDetailsResponse );
			  foodDetailsResponsesList.add( foodDetailsResponse);
		  }
		return foodDetailsResponsesList;
	}
}
