package com.microtech.smartshop.repository;

import com.microtech.smartshop.entity.Client;
import com.microtech.smartshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClient(Client client);
}