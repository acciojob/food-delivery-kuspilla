package com.driver.service.impl;


import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service

   public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
   public OrderDto createOrder(OrderDto orderDto) {

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderDto,orderEntity);
        String orderId = String.valueOf(new SecureRandom());
        orderDto.setOrderId(orderId);
        orderEntity = orderRepository.save(orderEntity);
        orderDto.setOrderId(orderEntity.getOrderId());

   return orderDto;
}

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        if(orderRepository.findByOrderId(orderId) == null){
            throw new Exception(orderId);
        }
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderEntity, orderDto);
        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
         OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
         if( orderEntity == null ) { // is not present order
             throw new Exception(orderId);
         }
         BeanUtils.copyProperties(order, orderEntity);
         orderEntity = orderRepository.save(orderEntity);
         order.setId(orderEntity.getId());
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        if( orderEntity == null ){
            throw new Exception(orderId);
        }
        orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto> orderDtosList = new ArrayList<>();
        Iterable<OrderEntity> orderEntityIterable = orderRepository.findAll();
        for( OrderEntity temp : orderEntityIterable){
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(temp, orderDto);
            orderDtosList.add(orderDto);
        }

        return orderDtosList;
    }
}