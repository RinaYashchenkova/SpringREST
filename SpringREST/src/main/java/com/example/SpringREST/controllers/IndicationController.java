package com.example.SpringREST.controllers;

import com.example.SpringREST.dto.IndicationDTO;
import com.example.SpringREST.models.Indication;
import com.example.SpringREST.services.IndicationService;
import com.example.SpringREST.util.IndicationErrorResponse;
import com.example.SpringREST.util.IndicationNotCreatedException;
import com.example.SpringREST.util.MeasurerNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/indications")
public class IndicationController {
    private final IndicationService indicationService;
    private final ModelMapper modelMapper;

    @Autowired
    public IndicationController(IndicationService indicationService, ModelMapper modelMapper) {
        this.indicationService = indicationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<IndicationDTO> allIndications(){
        return indicationService.allIndications().stream()
                .map(this::convertToIndicationDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainingDay")
    public List<IndicationDTO> allRainingDay(){
        return indicationService.findAllRainingDay().stream()
                .map(this::convertToIndicationDTO).collect(Collectors.toList());
    }

    @PostMapping("/add-from-measurer/{id}")
    public void addIndicationsFromMeasurer(@PathVariable("id")int id){
        indicationService.addIndicationsFromMeasurer(id);
    }

    @PostMapping("/add-from-one-measurer/{id}")
    public ResponseEntity<HttpStatus> addOneIndicationFromMeasurer(@RequestBody @Valid Indication indication,
                                             @PathVariable("id")int id, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new MeasurerNotCreatedException(errorMsg.toString());
        }
        indicationService.addOneIndication(indication,id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/add-1000-from-measurer/{id}")
    public void add1000Indication(@PathVariable("id")int id){
        indicationService.add1000Indication(id);
    }

    private Indication convertToIndication (IndicationDTO indicationDTO){
        return modelMapper.map(indicationDTO,Indication.class);
    }

    private IndicationDTO convertToIndicationDTO(Indication indication){
        return modelMapper.map(indication,IndicationDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<IndicationErrorResponse> handleException(IndicationNotCreatedException e){
        IndicationErrorResponse errorResponse = new IndicationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
