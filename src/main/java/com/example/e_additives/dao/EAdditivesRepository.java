package com.example.e_additives.dao;

import com.example.e_additives.entity.EAdditive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EAdditivesRepository extends JpaRepository<EAdditive, Integer> {
    List<EAdditive> findAll();

    EAdditive findByIndex(String index);
}
