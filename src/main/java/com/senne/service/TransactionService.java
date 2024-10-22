package com.senne.service;

import java.util.List;

import com.senne.modal.Order;
import com.senne.modal.Seller;
import com.senne.modal.Transaction;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySeller(Seller seller);   
    List<Transaction> getAllTransactions(); 
}
