package com.borelanjo.despesas.presentation.dto;

import java.io.Serializable;

public class TransferDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long destinationId;

    private Double value;

    public TransferDTO() {
    }

    public TransferDTO(Long destinationId, Double value) {
        this.destinationId = destinationId;

        this.value = value;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
