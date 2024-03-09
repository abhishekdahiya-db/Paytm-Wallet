package com.wallet.transaction.controller;


import com.wallet.transaction.DTO.TransactionDetailDTO;
import com.wallet.transaction.DTO.TransactionStartDTO;
import com.wallet.transaction.dao.ITransactionRepo;
import com.wallet.transaction.entity.Transaction;
import com.wallet.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private ITransactionRepo repository;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/start")
    private ResponseEntity<String> startTransaction(@RequestBody @Valid TransactionStartDTO transactionStartDTO){
        String transactionId = transactionService.initTransaction(transactionStartDTO);
        return new ResponseEntity<>(transactionId, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{transactionId}")
    private ResponseEntity<TransactionDetailDTO> getTransactionDetails(@PathVariable("transactionId") String transactionId){
        Transaction transaction = repository.findByTransactionId(transactionId);
        TransactionDetailDTO transactionDetailDTO = TransactionDetailDTO.builder()
                .status(transaction.getStatus().toString())
                .reason(transaction.getReason())
                .build();
        return ResponseEntity.ok(transactionDetailDTO);
    }
}
