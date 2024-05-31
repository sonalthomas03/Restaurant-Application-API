package com.Restaurant.application.repository;

import com.Restaurant.application.entity.Order;
import com.Restaurant.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
