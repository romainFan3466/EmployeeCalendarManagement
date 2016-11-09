package com.main.model;

import com.sun.istack.internal.NotNull;
import javafx.beans.DefaultProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by romain on 27/09/16.
 */
@Entity
@Table(name = "user")
public class User implements Serializable, Comparable<User> {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "surname")
    @NotNull
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
//    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    public User() {}

    public User(String name, String password, UserRole role){
        this.name = name;
        this.password= password;
        this.role = role;
    }

//    public User(String name, String surname, UserRole role) {
//        this.name = name;
//        this.surname = surname;
//        this.role = role;
//    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isEmployee (UserRole role ){ return this.role == UserRole.EMPLOYEE; }


    public String getAbreviation(){
        return this.name.substring(0,1).toUpperCase() + ". "+ this.surname;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!name.equals(user.name)) return false;
        if (!surname.equals(user.surname)) return false;
        return role == user.role;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public int compareTo(User u){
        return u.id - this.id;
    }
}
