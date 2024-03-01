package com.example.SpringREST.repositories;

import com.example.SpringREST.models.Measurer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasurerRepository extends JpaRepository<Measurer,Integer> {
    Optional<Measurer> findByMeasurerName(String name);
}
