package com.app.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discount")
@SQLDelete(sql = "update discount set deleted = true where ID=?")
@Where(clause = "deleted = false")
@Entity
@ToString
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    private float percent;

    @Column
    private boolean expired;

    @Column
    private boolean deleted = false;
}
