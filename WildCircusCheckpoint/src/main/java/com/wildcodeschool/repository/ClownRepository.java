package com.wildcodeschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.entity.Clown;


@Repository
public interface ClownRepository  extends JpaRepository<Clown, Long>{

}
