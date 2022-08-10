package com.example.demo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.exception.NotfoundException;
import com.example.demo.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

    private static Map<String, Product> productRepo = new HashMap<>();


    @Override
    public void createProduct(Product product) {
        productRepo.put(product.getId(), product);        
    }

    @Override
    public void updateProduct(String id, Product product) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepo.containsKey(id))
            throw new NotfoundException();

        productRepo.remove(id);
        
    }

    @Override
    public Collection<Product> getProducts() {
        return productRepo.values();
    }
    
}
