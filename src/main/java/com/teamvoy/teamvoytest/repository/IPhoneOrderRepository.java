package com.teamvoy.teamvoytest.repository;

import com.teamvoy.teamvoytest.model.IPhoneOrder;
import com.teamvoy.teamvoytest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPhoneOrderRepository extends JpaRepository<IPhoneOrder, Integer> {
    List<IPhoneOrder> findByOrderIn(List<Order> orders);
}
