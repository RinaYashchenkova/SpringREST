package com.example.SpringREST.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeasurerErrorResponse {
    private String errorMessage;
    private long timestamp;
}
