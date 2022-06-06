package com.example.e_additives.repository;

import com.example.e_additives.entity.EAdditive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс-репозиторий для получения информации из базы данных.
 * {@code findAll()} - метод, возвращающий все пищевые добавки {@link EAdditive} из базы данных;
 * {@code findBuIndex()} - метод, возвращающий определенную пищевую добавку {@link EAdditive} из базы данных.
 */
@Repository
public interface EAdditivesRepository extends JpaRepository<EAdditive, Integer> {

    List<EAdditive> findAll();

    EAdditive findByIndex(String index);

}
