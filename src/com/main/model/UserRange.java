package com.main.model;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by romain on 01/11/16.
 */
@Entity
@Table(name = "userRange")
public class UserRange implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",
            foreignKey = @ForeignKey(name = "USER_ID_FK")
    )
    private User user;

    @ManyToOne
    @JoinColumn(name = "hour_range_id",
            foreignKey = @ForeignKey(name = "HOURRANGE_ID_FK")
    )
    private HourRange hourRange;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    protected UserRange(){
    }


    public UserRange(User user, HourRange hourRange, LocalDate date) {
        this.user = user;
        this.hourRange = hourRange;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public HourRange getHourRange() {
        return hourRange;
    }

    public void setHourRange(HourRange r) {
        this.hourRange = r;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
