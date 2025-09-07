package com.huzaifaq.LoomNex_EcommerceStore.Repo;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIgnoreCase(String category);
}
