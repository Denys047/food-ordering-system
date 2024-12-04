package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated", order.getId());
        return new OrderCreatedEvent(order, ZonedDateTime.now());
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} paid successfully", order.getId());
        return new OrderPaidEvent(order, ZonedDateTime.now());
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} approved successfully", order.getId());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> filerMessage) {
        order.initCancel(filerMessage);
        log.info("Order payment is cancelling for order id: {}", order.getId());
        return new OrderCancelledEvent(order, ZonedDateTime.now());
    }

    @Override
    public void cancelOrder(Order order, List<String> filerMessage) {
        order.cancel(filerMessage);
        log.info("Order with id: {} is cancelled", order.getId());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant with id: %s is not active".formatted(restaurant.getId()));
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItem ->
                restaurant.getProducts().forEach(restaurantProduct -> {
                    Product currentProduct = orderItem.getProduct();
                    if (currentProduct.equals(restaurantProduct)) {
                        currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),
                                restaurantProduct.getPrice());
                    }
                }));
    }

}
