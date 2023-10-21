package com.example.minor1.controllers;

import com.example.minor1.dtos.IntiateTransactionRequest;
import com.example.minor1.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    // To intiate a transaction we need - book id, student id, admin id and transaction type. So we can create a seprate dtos for this
    @PostMapping("/transaction")
    public String intiateTxn(@RequestBody @Valid IntiateTransactionRequest transactionRequest){  // We will return the transaction id as response (we already have TxnId field in Transaction Class)

        return transactionService.intiateTxn(transactionRequest);

    }

    @PostMapping("/transaction/payment")
    public void makePayment(@RequestParam("amount") int amount, @RequestParam("studentId") int studentId,
                            @RequestParam("transactionId") String transactionId){

        transactionService.payFine(amount, studentId, transactionId);
    }
}
