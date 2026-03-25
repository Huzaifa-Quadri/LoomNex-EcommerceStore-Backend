package com.huzaifaq.LoomNex_EcommerceStore.Service;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Product;
import com.huzaifaq.LoomNex_EcommerceStore.Repo.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("No Product Found with this ID"));
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
