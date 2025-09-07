package com.huzaifaq.LoomNex_EcommerceStore.Service;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Product;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public List<Product> getAllProducts() {
        try {
            return productRepo.findAll();
        } catch (Exception e) {
            System.out.println("Error Loading all products"+e.getMessage());
        }

        return null;
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("No Product Found with this ID"));
    }

    public void addProductToRepo(Product product) {
        productRepo.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepo.deleteById(productId);
    }

    public List<Product> getProductByCategory(String category) {
        return productRepo.findByCategoryIgnoreCase(category);
    }
}

















