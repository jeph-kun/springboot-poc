package com.example.demo.product.controller;

import com.example.demo.product.exceptions.ProductNotFoundException;
import com.example.demo.product.service.ProductService;
import com.example.demo.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {


    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping(path = "{productId}")
    public ResponseEntity<?> getProductDetails(@PathVariable("productId") Long id){
        try {
            return ResponseEntity.ok(productService.getProductDetails(id));
        }
        catch(ProductNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse(ex.getMessage()));
        }

    }

    @PostMapping
    public ResponseEntity<?> createNewProduct(@RequestBody Product product){

        try{
            productService.createNewProduct(product);
            return ResponseEntity.ok().body(successResponse("Product successfully created"));
        }
        catch(IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping(path = "{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long id, @RequestParam(required = false) Boolean isSoftDelete){
        try {
            productService.deleteProduct(id, isSoftDelete);
            return ResponseEntity.ok().body(successResponse("Product successfully removed"));
        }
        catch(ProductNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse(ex.getMessage()));

        }


    }


    @PutMapping(path = "{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long id,
                              @RequestParam(required = false) String productName,
                              @RequestParam(required = false) Double price){
        try{
            productService.updateProduct(id, productName, price);
            return ResponseEntity.ok().body(successResponse("Product successfully updated"));
        }
        catch(ProductNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse(ex.getMessage()));
        }
        catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse(ex.getMessage()));
        }

    }

    private Map<String, Object> errorResponse(String error) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("errorMessage", error);
        return errorResponse;
    }

    private Map<String, Object> successResponse(String successMessage) {
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("success", true);
        successResponse.put("message", successMessage);
        return successResponse;
    }




}
