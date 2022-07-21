package com.app.services;


import com.app.exceptions.NotFoundException;
import com.app.persistence.entities.CashBalance;
import com.app.persistence.entities.Transaction;
import com.app.persistence.repositories.CashBalanceRepository;
import com.app.persistence.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashBalanceService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CashBalanceRepository cashBalanceRepository;

    public CashBalance addDeposit(int amount){
        var cash = new CashBalance();
        cash.setDeposit(amount);
        return cash;
    }

    public CashBalance addTransaction(long id, Transaction transaction){
        var cash = cashBalanceRepository.findById(id).orElseThrow();

        transaction.totalCalculation();
        cash.getPayementHistory().add(transaction);
        return cash;
    }

    public CashBalance updateBalance(long id, Transaction transaction){
        var cash = cashBalanceRepository.findById(id).orElse(addTransaction(id, transaction));

        transaction.totalCalculation();
        cash.getPayementHistory().add(transaction);
        return cash;
    }

    public void deleteTransaction(long idB, long idT){
        var cash = cashBalanceRepository.findById(idB).orElseThrow(() -> new NotFoundException("Unable to find the transaction" + idT));

        cash.getPayementHistory().stream()
                                 .forEach(trans -> {if (trans.getID() == idT){
                                                        cash.getPayementHistory().remove(trans);}});
    }

    public void softDeleteTransaction(long idB, long idTransaction){
        var cash = cashBalanceRepository.findById(idB).orElseThrow(() -> new NotFoundException("Unable to find the transaction " + idTransaction));

        cash.getPayementHistory().stream()
                                 .forEach(trans -> {if (trans.getID() == idTransaction)
                                                        {trans.setDeleted(true);}});
    }
}
