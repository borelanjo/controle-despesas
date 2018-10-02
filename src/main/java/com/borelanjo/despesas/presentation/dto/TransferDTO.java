package com.borelanjo.despesas.presentation.dto;

import java.io.Serializable;

public class TransferDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer destinationAccountNumber;

    private Double value;

    public TransferDTO() {
    }

    public TransferDTO(Integer destinationAccountNumber, Double value) {
        this.destinationAccountNumber = destinationAccountNumber;

        this.value = value;
    }

    public Integer getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public void setDestinationAccountNumber(Integer destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
