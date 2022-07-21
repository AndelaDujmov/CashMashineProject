package com.app.persistence.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "receipt_per_product")
@Data
@NoArgsConstructor
@ToString
@SQLDelete(sql = "update receipt_per_product set deleted = true where ID=?")
@Where(clause = "deleted = false")
public class ReceiptPerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    private long ID;

    @OneToOne(targetEntity = Product.class)
    private Product productName;

    @Column
    private double price;

    @Column
    private int quantity;

    @Column
    private double totalPrice                                                                                                                                        ;

    @Column
    private boolean deleted = false;

    public ReceiptPerProduct(long ID, Product productName, double price, int quantity, boolean deleted) {
        this.ID = ID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.deleted = deleted;
    }
}
