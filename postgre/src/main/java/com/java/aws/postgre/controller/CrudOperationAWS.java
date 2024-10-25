package com.java.aws.postgre.controller;

import com.java.aws.postgre.model.Sales;
import com.java.aws.postgre.repo.UserRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CrudOperationAWS {

    @Autowired
    UserRepo userRepo;

    @SneakyThrows
    @GetMapping("/getData/{id}")
    public Sales collectSalesRecord(@PathVariable long id) {
        Sales sales = userRepo.findById(id).orElseThrow(()-> new Exception("Given Id Not Present"));
        return sales;
    }

}
