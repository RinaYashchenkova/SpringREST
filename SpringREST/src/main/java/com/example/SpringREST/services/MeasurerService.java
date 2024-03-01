package com.example.SpringREST.services;

import com.example.SpringREST.models.Measurer;
import com.example.SpringREST.repositories.MeasurerRepository;
import com.example.SpringREST.util.MeasurerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurerService {
    private final MeasurerRepository measurerRepository;

    @Autowired
    public MeasurerService(MeasurerRepository measurerRepository) {
        this.measurerRepository = measurerRepository;
    }

    public Measurer findByMeasurerName(String name){
        return measurerRepository.findByMeasurerName(name).orElseThrow(MeasurerNotFoundException::new);
    }

    public List<Measurer> allMeasurer(){
        return measurerRepository.findAll();
    }

    @Transactional
    public Measurer createMeasurer(Measurer measurer){
        return measurerRepository.save(measurer);
    }

    public Measurer findById(int id){
        return measurerRepository.findById(id).
                orElseThrow(MeasurerNotFoundException::new);
    }
}
