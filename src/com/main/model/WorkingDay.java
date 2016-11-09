package com.main.model;


import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.*;

/**
 * Created by romain on 27/09/16.
 */

public class WorkingDay implements Serializable, Comparable<WorkingDay>{

    private TreeSet<EmployeeDay> employeeDays;

    private LocalDate date;


    public WorkingDay(){}

    public WorkingDay(LocalDate d){
        this.date = d;
        this.employeeDays = new TreeSet<EmployeeDay>();
    }

    public WorkingDay(LocalDate d, Collection<EmployeeDay> employeeDays){
        this(d);
        this.employeeDays.addAll(employeeDays);
    }

    public void addEmployeeDay(EmployeeDay ed){
        this.employeeDays.add(ed);
    }


//    /**
//     *
//     * @param date
//     * @param userRangeList , userRanges of collection must have the same date than date args
//     *
//     */
//    public WorkingDay(LocalDate date, Collection<UserRange> userRangeList) {
//        this(date);
//        HashMap<User, EmployeeDay> plan = new HashMap<>();
//
//        for (UserRange ur : userRangeList) {
//            User user = ur.getUser();
//            HourRange hourRange = (ur.getHourRange()==null)? new HourRange() : ur.getHourRange();
//            if (!plan.containsKey(user)) {
//                EmployeeDay ed = new EmployeeDay(user, date);
//                ed.addHourRange(hourRange, ur);
//                plan.put(user, ed);
//            } else {
//                EmployeeDay ed = plan.get(user);
//                ed.addHourRange(hourRange, ur);
//                plan.put(user, ed);
//            }
//        }
//        this.employeeDays.addAll(plan.values());
//    }


    public ArrayList<User> getWorkingEmployees(){
        ArrayList<User> usersList = new ArrayList<>();
        for(EmployeeDay d : this.employeeDays){
            usersList.add(d.getEmployee());
        }
        return usersList;
    }

    public ArrayList<EmployeeDay> getEmployeeDays(){
        return new ArrayList<EmployeeDay>(this.employeeDays);
    }


    public EmployeeDay getEmployeeDay( User employee) {
        for(EmployeeDay d : this.employeeDays){
            if(d.getEmployee().equals(employee)){
                return d;
            }
        }
        return null;
    }

//    public HashMap<HourRange, Integer> getPlanning(User u){
//        for(EmployeeDay d : this.employeeDays){
//            User e = d.getEmployee();
//            if(e.equals(u)){
//                return d.getHours();
//            }
//        }
//        return new HashMap<>();
//    }

    public LocalDate getDate(){
        return this.date;
    }


    public void applyColorModel(HashMap<Integer, ColorLine> userIdColorModel){
        for(EmployeeDay d : this.employeeDays){
            for(Map.Entry<Integer, ColorLine> entry : userIdColorModel.entrySet()){
                if(d.getEmployee().getId() == (int)entry.getKey()){
                    d.setColor(entry.getValue());
                }
            }
        }
    }

    @Override
    public int compareTo(WorkingDay w2) {
        return this.date.compareTo(w2.date);
    }




}
