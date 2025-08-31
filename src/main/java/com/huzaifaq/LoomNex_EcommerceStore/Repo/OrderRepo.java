package com.huzaifaq.LoomNex_EcommerceStore.Repo;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {

}
