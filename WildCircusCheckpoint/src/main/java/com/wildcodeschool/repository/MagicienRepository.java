package com.wildcodeschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.entity.Magicien;


@Repository
public interface MagicienRepository  extends JpaRepository<Magicien, Long>{

}
