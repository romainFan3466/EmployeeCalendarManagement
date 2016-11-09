//package com.main.dao;
//
//import com.main.model.EmployeeDay;
//import com.main.model.HourRange;
//import com.main.model.User;
//import com.main.model.UserRange;
//
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.ArrayList;
//import java.util.Map;
//
///**
// * Created by romain on 05/11/16.
// */
//@Stateless
//public class DAOEmployeeDay {
//
//    @PersistenceContext(unitName = "calendarEEPU")
//    private EntityManager em;
//
//    public EmployeeDay create(EmployeeDay empDay){
//
//        try{
//            UserRange ur = new UserRange(empDay.getEmployee(), null, empDay.getDate());
//            this.em.persist(ur);
//            HourRange hr = new HourRange();
//            EmployeeDay e = new EmployeeDay(empDay.getEmployee(),empDay.getDate());
//            e.addHourRange(hr, ur);
//            return e;
//        }catch (Exception e) {
//            return null;
//        }
//    }
//}
