package com.huzaifaq.LoomNex_EcommerceStore.Controller;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Product;
import com.huzaifaq.LoomNex_EcommerceStore.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getallproducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category){
        List<Product> list = productService.getProductByCategory(category);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getproduct/{id}")
    public Product getProductbyId(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public void addProduct(@Valid @RequestBody Product product){
        productService.addProductToRepo(product);
    }

    @DeleteMapping("delete/{id}")
    public void removeProduct(@PathVariable("id") Long productId){
        productService.deleteProduct(productId);
    }
}