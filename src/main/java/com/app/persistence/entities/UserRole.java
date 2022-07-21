package com.app.persistence.entities;

import com.app.utils.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Table(name = "role")
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Enumerated(EnumType.STRING)
    private UserType name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> userList;


}