package com.keycodehelp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity //This marks the class as a JPA entity and specifies the table name (roles) in the database. Each instance of this class represents a record in the roles table.
@Table(name = "roles")
public class Role {

    @Id //
    @GeneratedValue(strategy = GenerationType.IDENTITY) //specifies that the database will auto-generate this ID value.
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true) //This defines the name column in the table, which represents role names like ROLE_USER, ROLE_ADMIN, etc.
    private String name; // e.g., ROLE_USER, ROLE_ADMIN

    @ManyToMany(mappedBy = "roles") //This annotation establishes a many-to-many relationship between Role and User.
    private Set<User> users = new HashSet<>(); //This represents the collection of users who are assigned this role. For example, if you have a ROLE_ADMIN, this set will contain all users who have been assigned the ROLE_ADMIN role.
    //The HashSet ensures that there are no duplicate users in this set

    // Default constructor
    public Role() {}

    // Constructor with role name
    public Role(String name) {
        this.name = name;
    }
}
