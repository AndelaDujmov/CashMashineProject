package com.app.persistence.entities;

import com.app.utils.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name="transaction")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update transaction set deleted = true where ID=?")
@Where(clause = "deleted = false")
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @OneToMany(targetEntity = Receipt.class)
    private Set<Receipt> history;

    @Column
    private double total;

    @Column
    private String description;

    @Column
    private PaymentMethod paymentMethod;

    @Column
    private boolean deleted = false;

    public Transaction(long ID, double total, String description, boolean deleted) {
        this.ID = ID;
        this.total = total;
        this.description = description;
        this.deleted = deleted;
    }
    public void totalCalculation(){
        this.total = history.stream().collect(Collectors.summingDouble(h -> h.getFinalPrice()));
    }
}