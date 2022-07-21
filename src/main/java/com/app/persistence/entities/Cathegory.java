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
@Table(name = "cathegory")
@SQLDelete(sql = "update cathegory set deleted = true where ID=?")
@Where(clause = "deleted = false")
@Entity
@ToString
public class Cathegory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column
    private String name;

    @Column
    private boolean deleted = false;

}
