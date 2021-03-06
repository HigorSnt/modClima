package com.cyan.modclima.repositories;

import com.cyan.modclima.models.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    List<Field> findAllByCodeContainingIgnoreCase(String code);

}
