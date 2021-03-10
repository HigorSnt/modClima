package com.cyan.modclima.repositories;

import com.cyan.modclima.models.Mill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MillsRepository extends JpaRepository<Mill, Long> {

    List<Mill> findAllByNameContainingIgnoreCase(Pageable pageable, String name);

}
