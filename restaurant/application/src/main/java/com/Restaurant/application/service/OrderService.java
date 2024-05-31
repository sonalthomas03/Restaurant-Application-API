package com.Restaurant.application.service;

import com.Restaurant.application.config.JwtService;
import com.Restaurant.application.config.JwtTokenHolder;
import com.Restaurant.application.dto.OrderDto;
import com.Restaurant.application.dto.OrderItemDto;
import com.Restaurant.application.dto.OrderRequest;
import com.Restaurant.application.entity.MenuItem;
import com.Restaurant.application.entity.Order;
import com.Restaurant.application.entity.OrderItem;
import com.Restaurant.application.entity.User;
import com.Restaurant.application.enums.OrderStatus;
import com.Restaurant.application.exception.exceptions.entityNotExistException;
import com.Restaurant.application.exception.exceptions.tableEmptyException;
import com.Restaurant.application.repository.MenuRepository;
import com.Restaurant.application.repository.OrderRepository;
import com.Restaurant.application.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;


    @Transactional
    public OrderDto placeOrder(OrderRequest orderRequest) {
        String token = JwtTokenHolder.getToken();
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new entityNotExistException("User not found"));

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemDto -> {
            MenuItem menuItem = menuRepository.findByName(itemDto.getName())
                    .orElseThrow(() -> new entityNotExistException("Menu item not found"));
            return OrderItem.builder()
                    .menuItem(menuItem)
                    .quantity(itemDto.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        Order order = Order.builder()
                .user(user)
                .items(orderItems)
                .status(OrderStatus.PLACED)
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        Order savedOrder = orderRepository.save(order);
        return convertToOrderDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrder(Long id,OrderStatus newStatus)
    {
        Order order = orderRepository.findById(id).orElseThrow(() -> new entityNotExistException("Order not found"));
        order.setStatus(newStatus);
        Order savedOrder = orderRepository.save(order);
        return convertToOrderDto(savedOrder);

    }

    public List<OrderDto> getAllOrdersForCurrentUser() {
        String token = JwtTokenHolder.getToken();
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new entityNotExistException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);
        if (orders.isEmpty()) {
            throw new tableEmptyException("No orders found for the user.");
        }

        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

/*
    private OrderDto convertToOrderDto(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new entityNotExistException("Order not found"));
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(order.getUser().getFirstname() + " " + order.getUser().getLastname());
        orderDto.setItems(order.getItems().stream()
                .map(item -> new OrderItemDto(item.getMenuItem().getName(), item.getQuantity()))
                .collect(Collectors.toList()));
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }
*/

    private OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(order.getUser().getFirstname() + " " + order.getUser().getLastname());
        orderDto.setItems(order.getItems().stream()
                .map(item -> new OrderItemDto(item.getMenuItem().getName(), item.getQuantity()))
                .collect(Collectors.toList()));
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }
    private boolean isOrderTableEmpty()
    {
        return orderRepository.count() == 0;
    }

    public List<OrderDto> getAllOrders()
    {
        if(isOrderTableEmpty())
            throw new tableEmptyException("Order table empty.");
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());

    }


/*
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
 */


}

