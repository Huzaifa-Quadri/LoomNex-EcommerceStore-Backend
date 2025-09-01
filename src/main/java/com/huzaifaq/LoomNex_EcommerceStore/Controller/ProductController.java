package com.huzaifaq.LoomNex_EcommerceStore.Controller;

import com.huzaifaq.LoomNex_EcommerceStore.Model.Product;
import com.huzaifaq.LoomNex_EcommerceStore.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/getallproducts")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/getproduct/{id}")
    public Product getProductbyId(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public void addProduct(@RequestBody Product product){
        productService.addProductToRepo(product);
    }

    @DeleteMapping("delete/{id}")
    public void removeProduct(@PathVariable("id") Long productId){
        productService.deleteProduct(productId);
    }
}