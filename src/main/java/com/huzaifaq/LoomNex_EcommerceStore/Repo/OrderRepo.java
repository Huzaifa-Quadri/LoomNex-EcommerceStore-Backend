package com.huzaifaq.LoomNex_EcommerceStore.Repo;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o JOIN FETCH o.user")
    List<Orders> findAllOrdersWithUsers();

    @Query("select o from Orders o JOIN FETCH o.user u WHERE u.id = :userId")
    List<Orders> findByUserIdWithUsers(Long userId);
}
