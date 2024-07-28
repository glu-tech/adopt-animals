package com.glutech.adoptanimals.repository;

import com.glutech.adoptanimals.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
