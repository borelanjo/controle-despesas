package com.borelanjo.despesas.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.borelanjo.despesas.domain.enumeration.TransactionType;
import com.borelanjo.despesas.domain.model.Account;
import com.borelanjo.despesas.domain.model.TransactionHistory;
import com.borelanjo.despesas.domain.service.AccountService;
import com.borelanjo.despesas.infrastructure.service.ResponseServiceImpl;
import com.borelanjo.despesas.presentation.dto.ResponseTO;
import com.borelanjo.despesas.presentation.dto.TransferDTO;
import com.borelanjo.despesas.presentation.dto.account.AccountRequestTO;
import com.borelanjo.despesas.presentation.dto.account.AccountResponseTO;

import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService<Account, Long> accountService;
    
    @Autowired
    private ResponseServiceImpl responseService;
    
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<ResponseTO<AccountResponseTO>> createAccount(@RequestBody AccountRequestTO requestTO) {
        Account account = accountService.create(modelMapper.map(requestTO, Account.class));
        return responseService.ok(modelMapper.map(account, AccountResponseTO.class));
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseTO<AccountResponseTO>> getAccount(@PathVariable("id") Long id) {
        return responseService.ok(modelMapper.map(accountService.findById(id).get(), AccountResponseTO.class));
    }

    @GetMapping("/{id}/transactionHistory")
    @ResponseBody
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseTO<List<TransactionHistory>>> showHistory(@PathVariable("id") Long id) {

        return responseService.ok(accountService.showHistory(id));
    }

    @PatchMapping("/{id}/transactionHistory")
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<ResponseTO<TransactionHistory>> setBalance(@PathVariable("accountNumber") Integer accountNumber,
            @RequestBody TransactionHistory transactionHistory) {
        TransactionType transactionType = TransactionType.valueOf(transactionHistory.getType());
        return responseService.ok(accountService.addTransaction(accountNumber, transactionType, transactionHistory.getValue()));
    }

    @PutMapping("/{sourceAccountNumber}/transfer")
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<ResponseTO<TransactionHistory>> transfer(@PathVariable("sourceAccountNumber") Integer sourceAccountNumber,
            @RequestBody TransferDTO transferDTO) {

        return responseService.ok(accountService.transfer(sourceAccountNumber, transferDTO.getDestinationAccountNumber(),
                transferDTO.getValue()));
    }

}
