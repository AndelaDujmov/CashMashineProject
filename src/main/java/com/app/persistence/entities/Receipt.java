package com.app.persistence.entities;

import com.app.utils.complex.Address;
import com.app.utils.enums.CashType;
import com.app.utils.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receipt")
@SQLDelete(sql = "update product set deleted = true where ID=?")
@Where(clause = "deleted = false")
@Entity
@ToString
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    private String storeName;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private CashType cashType;

    @Column
    private Date date;

    @Embedded
    private Address address;

    @OneToMany(targetEntity = ReceiptPerProduct.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReceiptPerProduct> productList;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    private User user;

    @Column
    private double finalPrice;

    @Column
    private boolean deleted = false;

    public void finalPriceCalculation(){
        this.finalPrice = productList.stream().mapToDouble(product -> product.getPrice()).sum();
    }
}