package com.example.SpringREST.util;

import com.example.SpringREST.models.Measurer;
import com.example.SpringREST.services.MeasurerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurerValidator implements Validator {
    private final MeasurerService measurerService;

    @Autowired
    public MeasurerValidator(MeasurerService measurerService) {
        this.measurerService = measurerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurer measurer = (Measurer) target;
        Measurer measurerIsExists = measurerService.findByMeasurerName(measurer.getMeasurerName());
            if (measurerIsExists != null && measurer.getId() != measurerIsExists.getId()){
                errors.rejectValue("measurerName","",
                        "Сенсор с таким названием уже существует.");
            }
    }
}
