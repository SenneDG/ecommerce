package com.senne.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senne.modal.Order;
import com.senne.modal.Seller;
import com.senne.modal.Transaction;
import com.senne.repository.SellerRepository;
import com.senne.repository.TransactionRepository;
import com.senne.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();
        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setOrder(order);
        transaction.setCustomer(order.getUser());


        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySeller(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
