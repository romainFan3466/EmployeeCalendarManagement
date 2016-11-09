package com.main.model;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by romain on 29/09/16.
 */

@Entity
@Table(name = "hourRange")
public class HourRange implements Serializable, Comparable<HourRange>{


    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "begin")
    private LocalTime begin;


    @Column(name = "end")
    private LocalTime end;


    public HourRange(){}

    public HourRange(LocalTime begin, LocalTime end) {
        this.begin = begin;
        this.end = end;
    }

    public void update(LocalTime begin, LocalTime end){
        this.begin = begin;
        this.end = end;
    }

    public LocalTime getBegin() {
        return begin;
    }


    public LocalTime getEnd() {
        return end;
    }

    public void setBegin(LocalTime begin) {
        this.begin = begin;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }


    public boolean isIntoRange(LocalTime time){
        return (time.isAfter(this.begin) && time.isBefore(this.end));
    }

    public boolean isInRange(LocalTime time){
        return isOnEdge(time) || (time.isAfter(this.begin) && time.isBefore(this.end));
    }

    public boolean isOnEdge(LocalTime time){
        return this.begin.equals(time) || this.end.equals(time);
    }

    public boolean beginAt(LocalTime time){
        return this.begin.equals(time);
    }

    public boolean endAt(LocalTime time){
        return this.end.equals(time);
    }

    @Override
    public int compareTo(HourRange hr2) {
        if(this.equals(hr2)){
            return 0;
        }
        return (getBegin().isBefore(hr2.getBegin()))? -1 : 1;
    }
}
