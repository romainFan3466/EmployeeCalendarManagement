package com.main.model;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SortNatural;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by romain on 03/11/16.
 */
@Entity
@Table(name = "employeeDay")
public class EmployeeDay implements Serializable, Comparable<EmployeeDay> {

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
    private User employee;

    @OneToMany(fetch = FetchType.EAGER)
    @SortNatural
    private SortedSet<HourRange> hourRanges = new TreeSet<>();


    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @Transient
    private ColorLine color;


    public EmployeeDay(){}




    public EmployeeDay(User employee, LocalDate date){
        this.employee = employee;
        this.date = date;
        this.hourRanges = new TreeSet<>();
    }

    public void setColor(ColorLine color){
        this.color = color;
    }


    public User getEmployee() {
        return employee;
    }


    public LocalDate getDate() {
        return date;
    }

    public boolean isWorking(LocalTime time , boolean excludedSides){
        for(HourRange hr : this.hourRanges){
            if(excludedSides){
                if(hr.isIntoRange(time)){
                    return true;
                }
            }else {
                if(hr.isInRange(time)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isWorkingBetween(LocalTime begin , LocalTime end){
        return isWorking(begin.plusMinutes(1), false) || isWorking(end.minusMinutes(1), false);
    }

    public boolean beginAt(LocalTime time){
        for(HourRange hr : this.hourRanges){
            if(hr.beginAt(time)){
                return true;
            }
        }
        return false;
    }

    public boolean endAt(LocalTime time){
        for(HourRange hr : this.hourRanges){
            if(hr.endAt(time)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(EmployeeDay e2) {
        return (int) (this.getEmployee().getId() - e2.getEmployee().getId());
    }
}
