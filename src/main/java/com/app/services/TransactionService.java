package com.app.services;


import com.app.persistence.entities.Costumer;
import com.app.persistence.entities.Receipt;
import com.app.persistence.entities.Transaction;
import com.app.persistence.entities.User;
import com.app.persistence.repositories.CostumerRepository;
import com.app.persistence.repositories.ReceiptRepository;
import com.app.persistence.repositories.TransactionRepository;
import com.app.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CostumerRepository costumerRepository;

    public void addHistory(User user, Costumer costumer, Receipt receipt, String description){
        var transaction = new Transaction();
        userRepository.save(user);
        costumerRepository.save(costumer);
        transaction.getHistory().add(receipt);
        transaction.setDeleted(false);
        transaction.setDescription(description);
        transaction.totalCalculation();
        transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Transaction update, long id){
        var transaction = transactionRepository.findByID(id);
        transaction.setDescription(transaction.getDescription());
        transaction.setHistory(transaction.getHistory());
        transaction.totalCalculation();
        transactionRepository.save(transaction);
        return transaction;
    }

    public void softDeleteTransaction(long id){
        var transaction = transactionRepository.findByID(id);

        transaction.getHistory().stream()
                .forEach(trans -> {if(trans.isDeleted() == true)
                            {transaction.getHistory().remove(trans);}});
    }

    public void returnCash(User user){
        userRepository.save(user);
    }

    public void deleteTransaction(long id){
        transactionRepository.deleteById(id);
    }

    public void deleteAll(){
        transactionRepository.deleteAll();
    }
}
