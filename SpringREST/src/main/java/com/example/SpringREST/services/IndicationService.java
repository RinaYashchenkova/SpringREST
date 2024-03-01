package com.example.SpringREST.services;

import com.example.SpringREST.models.Indication;
import com.example.SpringREST.models.Measurer;
import com.example.SpringREST.repositories.IndicationRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class IndicationService {
    private final IndicationRepository indicationRepository;
    private final MeasurerService measurerService;


    @Autowired
    public IndicationService(IndicationRepository indicationRepository, MeasurerService measurerService) {
        this.indicationRepository = indicationRepository;
        this.measurerService = measurerService;
    }

    public List<Indication> allIndications(){
        return indicationRepository.findAll();
    }

    public List<Indication> findAllRainingDay(){
        return indicationRepository.findAllByRainingIsTrue();
    }

    @Transactional
    public void addIndicationsFromMeasurer(int measurerId){
        Measurer measurer = measurerService.findById(measurerId);

        Random random = new Random();
        double value = random.nextDouble() * 200 - 100;
        boolean raining = random.nextBoolean();
        LocalDateTime createdAt = LocalDateTime.now();

        Indication indication = new Indication();
        indication.setMeasurer(measurer);
        indication.setValue(value);
        indication.setRaining(raining);
        indication.setCreatedAt(createdAt);

        indicationRepository.save(indication);
    }

    @Transactional
    public void addOneIndication(Indication indication,int measurerId){
        Measurer measurer = measurerService.findById(measurerId);
        indication.setMeasurer(measurer);
        enrichIndication(indication);
        indicationRepository.save(indication);
    }

    @Transactional
    public void add1000Indication(int measurerId){
        Measurer measurer = measurerService.findById(measurerId);

        List<Indication> indications = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Indication indication = new Indication();
            indication.setValue(Math.random() * 200 - 100);
            indication.setRaining(Math.random() < 0.5);
            indication.setCreatedAt(LocalDateTime.now());
            indication.setMeasurer(measurer);
            indications.add(indication);
        }

        List<List<Indication>> parts = Lists.partition(indications, 500);


        for (List<Indication> part : parts) {
            indicationRepository.saveAll(part);
        }
    }

    @Transactional
    public void enrichIndication(Indication indication){
        indication.setCreatedAt(LocalDateTime.now());
    }
}
