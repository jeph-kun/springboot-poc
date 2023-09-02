package com.example.demo.product.service;

import com.example.demo.product.exceptions.ProductNotFoundException;
import com.example.demo.product.repository.ProductRepository;
import com.example.demo.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getProducts(){
        return productRepository.getAllProduct();
    }

    public void createNewProduct(Product product) {

        Optional<Product> searchProductName = productRepository.findProductByProductNameLike(product.getProductName());

        if(searchProductName.isPresent()){
            throw new IllegalStateException("There is a product with a similar name");
        }
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);
    }


    @Transactional
    public void deleteProduct(Long id, Boolean isSoftDelete) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Cannot find the product with an id of (" + id + ")"));

        if(isSoftDelete){
            product.setDeletedAt(LocalDateTime.now());
        } else {
            productRepository.deleteById(id);
        }

    }


    @Transactional
    public void updateProduct(Long id, String productName, Double price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Cannot find the product with an id of (" + id + ")"));

        Optional<Product> searchProductName = productRepository.findProductByProductNameLike(productName);

        if(searchProductName.isPresent()){
            throw new IllegalStateException("This is name is already taken");
        }
        else {
            if(!productName.isEmpty()){
                product.setProductName(productName);
            }
            else {
                product.setProductName(product.getProductName());
            }
        }

        product.setPrice(price);
    }

    public Product getProductDetails(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Cannot find the product with an id of (" + id + ")"));
    }

}


