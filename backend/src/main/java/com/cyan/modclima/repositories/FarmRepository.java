package com.cyan.modclima.repositories;

import com.cyan.modclima.models.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long>, CustomRepository {

}
