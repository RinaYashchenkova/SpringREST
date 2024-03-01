package com.example.SpringREST.controllers;

import com.example.SpringREST.dto.MeasurerDTO;
import com.example.SpringREST.models.Measurer;
import com.example.SpringREST.services.MeasurerService;
import com.example.SpringREST.util.MeasurerErrorResponse;
import com.example.SpringREST.util.MeasurerNotCreatedException;
import com.example.SpringREST.util.MeasurerNotFoundException;
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
@RequestMapping("/sensors")
public class MeasurerController {
    private final MeasurerService measurerService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurerController(MeasurerService measurerService, ModelMapper modelMapper) {
        this.measurerService = measurerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<MeasurerDTO> measurerList(){
        return measurerService.allMeasurer().stream().
                map(this::converToMeasurerDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurerDTO findMeasurer(@PathVariable("id")int id){
        return converToMeasurerDTO(measurerService.findById(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurerDTO measurerDTO,
                                             BindingResult bindingResult){
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
        measurerService.createMeasurer(convertToMeasurer(measurerDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurerErrorResponse> handleException(MeasurerNotFoundException e){
        MeasurerErrorResponse errorResponse = new MeasurerErrorResponse(
                "Сенсор с таким именем не найден",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurerErrorResponse> handleException(MeasurerNotCreatedException e){
        MeasurerErrorResponse errorResponse = new MeasurerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    private Measurer convertToMeasurer(MeasurerDTO measurerDTO){
        return modelMapper.map(measurerDTO,Measurer.class);
    }

    private MeasurerDTO converToMeasurerDTO(Measurer measurer){
        return modelMapper.map(measurer,MeasurerDTO.class);
    }
}
