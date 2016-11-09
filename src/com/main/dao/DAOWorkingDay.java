package com.main.dao;

import com.main.dao.exception.DAOException;
import com.main.model.*;
import org.joda.time.LocalDate;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

/**
 * Created by romain on 01/11/16.
 */
@Stateless
public class DAOWorkingDay {

    @PersistenceContext(unitName = "calendarEEPU")
    private EntityManager em;

    public WorkingDay getWorkingDay(LocalDate date) {
        WorkingDay wd = new WorkingDay(date);

        Collection<EmployeeDay> employeeDays = null;
        String JPQL = "SELECT ed FROM EmployeeDay ed WHERE date= :date";
        try {
            Query req = this.em.createQuery(JPQL);
            req.setParameter("date", date);
            employeeDays = (java.util.Collection<EmployeeDay>) req.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return new WorkingDay(date, employeeDays);
    }



    public ArrayList<WorkingDay> getWorkingDays(LocalDate start, LocalDate end){
        Collection<EmployeeDay> employeeDays = null;
        String JPQL = "SELECT ed FROM EmployeeDay ed WHERE ed.date >= :start AND ed.date <= :e ORDER BY ed.date ASC ";
        try {
            Query req = this.em.createQuery(JPQL);
            req.setParameter("start", start);
            req.setParameter("e", end);
            employeeDays = (java.util.Collection<EmployeeDay>) req.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }

        HashMap<LocalDate, WorkingDay> planning = new HashMap<>();

        for(EmployeeDay ed : employeeDays){
            LocalDate date = ed.getDate();
            if(!planning.containsKey(date)) {
                planning.put(date, new WorkingDay(date));
            }
            WorkingDay w = planning.get(date);
            w.addEmployeeDay(ed);
            planning.put(date, w);
        }

        ArrayList<WorkingDay> days = new ArrayList<>(planning.values());
        Collections.sort(days);
        return days;
    }


    public void createEmployeeDay(EmployeeDay ed){
        try{
            this.em.persist(ed);
        } catch (Exception e){
            String s = "shit";
        }
    }

    public void deleteEmployeeDay(EmployeeDay ed){
        try{
            this.em.remove(em.contains(ed) ? ed : em.merge(ed));
        } catch (Exception e){
            String s = "shit";
        }
    }


}
