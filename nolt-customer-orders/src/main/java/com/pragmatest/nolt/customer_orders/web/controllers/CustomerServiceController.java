package com.pragmatest.nolt.customer_orders.web.controllers;

import com.pragmatest.nolt.customer_orders.services.CustomerOrdersService;
import com.pragmatest.nolt.customer_orders.services.models.Order;
import com.pragmatest.nolt.customer_orders.web.controllers.requests.SubmitOrderRequest;
import com.pragmatest.nolt.customer_orders.web.controllers.responses.GetOrderResponse;
import com.pragmatest.nolt.customer_orders.web.controllers.responses.SubmitOrderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerServiceController {

    @Autowired
    CustomerOrdersService customerOrdersService;

    @Autowired
    ModelMapper mapper;

    @PostMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubmitOrderResponse> submit(@RequestHeader(name = "X-Customer-Id") String customerId, @RequestBody SubmitOrderRequest request) {

        Order orderSubmission = mapper.map(request, Order.class);
        orderSubmission.setCustomerId(customerId);

        String orderId = customerOrdersService.submitOrder(orderSubmission);

        return ResponseEntity.ok(new SubmitOrderResponse(orderId));
    }

    @GetMapping(value = "orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetOrderResponse> get(@RequestHeader(name = "X-Customer-Id") String customerId, @PathVariable String orderId) {

        Order order = customerOrdersService.getOrder(orderId, customerId);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        GetOrderResponse getOrderResponse = mapper.map(order, GetOrderResponse.class);
        return ResponseEntity.ok(getOrderResponse);
    }
}
