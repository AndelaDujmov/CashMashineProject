package com.app.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "costumer")
@SQLDelete(sql = "update costumer set deleted = true where ID=?")
@Where(clause = "deleted = false")
@ToString
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    private String name;

    @Column
    private String cardNumber;

    @Column
    private boolean deleted = false;
}