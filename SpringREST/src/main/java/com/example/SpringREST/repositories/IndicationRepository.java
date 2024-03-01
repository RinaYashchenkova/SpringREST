package com.example.SpringREST.repositories;

import com.example.SpringREST.models.Indication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IndicationRepository extends JpaRepository<Indication,Integer> {
    List<Indication> findAllByRainingIsTrue();
}
