package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
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
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto  = userService.getUser(id);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userDto,userResponse);
		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserResponse userResponse = new UserResponse();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);
		userDto = userService.createUser(userDto);
		BeanUtils.copyProperties(userDto,userResponse);
		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
        UserResponse userResponse = new UserResponse();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails,userDto);
		userDto = userService.updateUser(id,userDto);
		BeanUtils.copyProperties(userDto, userResponse);
		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		UserDto userDto = new UserDto();
		try {
			userService.deleteUser(id);
			operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
			return operationStatusModel;
		}
		catch (Exception e){
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
			return operationStatusModel;
		}
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
       List<UserResponse> userResponsesList = new ArrayList<>();
	   List<UserDto> userDtoList = userService.getUsers();
	   for(UserDto temp : userDtoList){
		   UserResponse userResponse = new UserResponse();
		   BeanUtils.copyProperties(temp,userResponse);
		   userResponsesList.add(userResponse);
	   }

		return userResponsesList;
	}
	
}
