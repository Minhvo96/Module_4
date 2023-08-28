package com.codegym.demoproduct.Service;

import com.codegym.demoproduct.Model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {
    List<Product> productList = List.of(
            new Product(1L,"Bun", 20000D),
            new Product(1L,"Pho", 25000D)
    );
    public List<Product> findAll(){
        return productList;
    }
    public void save(Product product){
        productList.add(product);
    }
}
