package com.project.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.task.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
