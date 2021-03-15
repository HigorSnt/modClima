package com.cyan.modclima.repositories;

import com.cyan.modclima.models.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {

    List<Harvest> findAllByStartBetween(LocalDate start, LocalDate end);

    List<Harvest> findAllByStartIsGreaterThanEqual(LocalDate start);

    List<Harvest> findAllByEndIsLessThanEqual(LocalDate end);
}
