package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
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
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{

		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		OrderDto orderDto = orderService.getOrderById(id);
		BeanUtils.copyProperties(orderDto, orderDetailsResponse);
		return orderDetailsResponse;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {

		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order,orderDto);
		orderDto = orderService.createOrder(orderDto);
		BeanUtils.copyProperties(orderDto, orderDetailsResponse);
		return orderDetailsResponse;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{

		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order,orderDto);
		orderDto = orderService.updateOrderDetails(id, orderDto);
		BeanUtils.copyProperties(orderDto,orderDetailsResponse);
		return orderDetailsResponse;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		try{
			orderService.deleteOrder(id);
			operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
			return operationStatusModel;
		}
		catch (Exception e ){
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
			return operationStatusModel;
		}

	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		 List<OrderDetailsResponse> orderDetailsResponsesList = new ArrayList<>();
		 List<OrderDto> orderDtosList = orderService.getOrders();
		 for( OrderDto temp : orderDtosList){
			 OrderDetailsResponse orderDetailsResponses = new OrderDetailsResponse();
			 BeanUtils.copyProperties(temp, orderDetailsResponses);
			 orderDetailsResponsesList.add(orderDetailsResponses);
		 }

		return orderDetailsResponsesList;
	}
}
