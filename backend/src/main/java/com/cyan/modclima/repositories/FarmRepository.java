package com.cyan.modclima.repositories;

import com.cyan.modclima.models.Farm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    List<Farm> findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(Pageable pageable, String code, String name);

}
