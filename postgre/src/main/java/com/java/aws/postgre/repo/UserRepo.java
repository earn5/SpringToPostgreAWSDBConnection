package com.java.aws.postgre.repo;

import com.java.aws.postgre.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Sales, Long> {
}
