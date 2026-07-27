package com.example.crud_javasql.controllers;

import com.example.crud_javasql.dtos.ProductDto;
import com.example.crud_javasql.model.Product;
import com.example.crud_javasql.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository repository;

    //READ
    @GetMapping
    public ResponseEntity getAll(){
        List<Product> listProducts = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProducts);
    }

    //READ
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") Integer id) {
        Optional product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(product.get());
    }

    //CREATE
    @PostMapping
    public ResponseEntity save(@RequestBody ProductDto dto){
        var product = new Product();
        BeanUtils.copyProperties(dto, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id){
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        repository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable(value = "id")Integer id, @RequestBody ProductDto dto){
        Optional<Product> product = repository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        var pdModel = product.get();
        BeanUtils.copyProperties(dto,pdModel);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(pdModel));
    }

}
