package com.test.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.springboot.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByNameAndDescription(String name,String description);
}
