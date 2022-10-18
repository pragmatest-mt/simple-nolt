package com.pragmatest.nolt.customer_orders.services;

import com.pragmatest.nolt.customer_orders.data.entities.OrderEntity;
import com.pragmatest.nolt.customer_orders.data.repositories.CustomerOrdersRepository;
import com.pragmatest.nolt.customer_orders.services.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerOrdersService {

    @Autowired
    CustomerOrdersRepository repository;

    @Autowired
    ModelMapper mapper;

    public String submitOrder(Order order) {
        OrderEntity orderEntity = mapper.map(order, OrderEntity.class);
        orderEntity.setId(UUID.randomUUID().toString());
        orderEntity = repository.save(orderEntity);
        return orderEntity.getId();
    }
}
