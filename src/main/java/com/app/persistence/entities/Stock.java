package com.app.persistence.entities;

import com.app.utils.complex.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock")
@Entity
@SQLDelete(sql = "update stock set deleted = true where ID=?")
@Where(clause = "deleted = false")
@ToString
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Embedded
    private Address address;

    @OneToOne(targetEntity = Product.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Product product;

    @Column
    private int quantityPerProduct;

    @Column
    private boolean deleted = false;
}
