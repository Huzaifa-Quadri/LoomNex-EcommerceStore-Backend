package com.huzaifaq.LoomNex_EcommerceStore.Repo;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {

    @Query(value = "select o from Orders o JOIN FETCH o.user") //OR we could've done as JPA QIAry is different from Native SQL Query :-
    //* @Query(value = "select o.id, o.status, u.name from Orders o INNER JOIN User u ON o.order_id = u.user_id, nativeQuery = true")

    List<Orders> findAllOrdersWithUsers();
}
