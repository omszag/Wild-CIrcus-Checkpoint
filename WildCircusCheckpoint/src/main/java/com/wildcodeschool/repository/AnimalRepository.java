package com.wildcodeschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.entity.Animal;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{

}
