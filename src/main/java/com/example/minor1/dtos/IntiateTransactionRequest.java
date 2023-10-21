package com.example.minor1.dtos;

import com.example.minor1.domain.TransactionType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IntiateTransactionRequest {
    @NotNull
    private int studentId;
    @NotNull
    private int bookId;
    @NotNull
    private int adminId;
    @NotNull
    private TransactionType transactionType;


}
