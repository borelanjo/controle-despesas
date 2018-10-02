package com.borelanjo.despesas.infrastructure.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.borelanjo.despesas.presentation.dto.ResponseTO;

@Service
public class ResponseServiceImpl {

    public <T> ResponseEntity<ResponseTO<T>> create(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseTO<T>(data));
    }

    public <T> ResponseEntity<ResponseTO<T>> ok(T data) {
        return ResponseEntity.ok(new ResponseTO<T>(data));
    }

    public <T> ResponseEntity<T> accepted() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    public <T> ResponseEntity<T> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    public <T> ResponseEntity<T> noContent() {
        return ResponseEntity.noContent().build();
    }
}
