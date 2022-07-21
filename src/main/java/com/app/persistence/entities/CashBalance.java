package com.app.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Table(name = "cash_balance")
@SQLDelete(sql = "update cash_balance set deleted = true where ID=?")
@Where(clause = "deleted = false")
@Proxy(lazy = false)
@Entity
@ToString
public class CashBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    private double deposit;

    @Column
    private double totalMoney;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Transaction.class)
    private Set<Transaction> payementHistory;

    @Column
    private boolean deleted = false;

    public CashBalance(long ID, double deposit, boolean deleted) {
        this.ID = ID;
        this.deposit = deposit;
        this.deleted = deleted;
    }

    public void totalMoneyCalculation(){
        totalMoney = payementHistory.stream().collect(Collectors.summingDouble(p -> p.getTotal()));
    }
}



