package com.app.persistence.entities;

import com.app.utils.enums.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "product")
@SQLDelete(sql = "update product set deleted = true where ID=?")
@Where(clause = "deleted = false")
@Entity
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    private String name;

    @Column(unique = true)
    private String productCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Discount.class)
    private Discount discount;

    @Column
    private double price;



    @ManyToOne(targetEntity = Cathegory.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cathegory cathegory;

    @Column
    private boolean deleted = false;

    public Product(long ID, String name, double price, boolean deleted) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.deleted = deleted;
    }
}
