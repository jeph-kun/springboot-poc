package com.example.demo.product.config;

import com.example.demo.product.repository.ProductRepository;
import com.example.demo.product.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.time.LocalDateTime;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository repository) {
        return args -> {
            Product alien = new Product(
                    "Alienware",
                    15000.00,
                    LocalDateTime.now(),
                    null,
                    null
            );

            Product msi = new Product(
                    "MSI",
                    50000.00,
                    LocalDateTime.now(),
                    null,
                    null
            );

            repository.saveAll(List.of(alien, msi));
        };
    }
}
