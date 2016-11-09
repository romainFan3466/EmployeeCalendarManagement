package com.main.bean;

import com.main.dao.DAOUser;
import com.main.dao.DAOWorkingDay;
import com.main.model.*;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Created by romain on 28/10/16.
 */


@SessionScoped
@Named("calendarBean")
public class CalendarBean implements Serializable {

    private static long serialVersionUID = 1L;

    @EJB
    private DAOUser daoUser ;

    @EJB
    private DAOWorkingDay daoWorkingDay;


    private ArrayList<User> employees;
    private ArrayList<User> workingEmployees;
    private LocalDate todayDate, viewDate;

    private ArrayList<User> remainingEmployees;
    private int val5;
    private ArrayList<ColorLine> colors;

    private ArrayList<WorkingDay> week;

    private String inputEmployee;


    @PostConstruct
    public void postConst(){
        this.todayDate = new LocalDate();
        this.colors = ColorLine.getValues();
        this.viewDate =  new LocalDate();
    }

    public void init() {
        this.employees = (ArrayList<User>) this.daoUser.getAllEmployee();
        this.todayDate = new LocalDate();
        this.viewDate =  new LocalDate();
        initEmployees();
    }


    public void initHourModal(EmployeeDay employeeDay){
        String s = null;
    }


    public String getDayDate(int i){
        return this.viewDate.withDayOfWeek(i).toString("dd/MM");

    }

    private  void initEmployees(){
        this.week = this.getWeek(this.viewDate);
        this.workingEmployees = this.generateWorkingEmployees();
        this.remainingEmployees = new ArrayList<User>(this.employees);
        this.remainingEmployees.removeAll(this.workingEmployees);
    }

    public String nextWeek(){
        this.viewDate = this.viewDate.plusWeeks(1);
        initEmployees();
        return "";
    }
    public String previousWeek(){
        this.viewDate = this.viewDate.minusWeeks(1);
        initEmployees();
        return "";
    }

    public ArrayList<User> getRemainingEmployees() {
        return remainingEmployees;
    }

    public String getColor(int index){
        return this.colors.get(index).toString();
    }

    public void setRemainingEmployees(List<User> remainingEmployees) {
        this.remainingEmployees = (ArrayList<User>) remainingEmployees;
    }

    public int getVal5() {
        return val5;
    }

    public String getRemainingEmployeeList(){
        String n="";
        for(User u : this.remainingEmployees){
            n+=u.getSurname();
            n+=",";
        }
        if(n.length()>0){
                n = n.substring(0, n.length()-1);
            }
        return n;
    }

    public void setVal5(int val5) {
        this.val5 = val5;
    }

    private User getBySurname(String surname){
        for(User u : this.remainingEmployees){
            if(surname.equals(u.getSurname())){
                return u;
            }
        }
        return null;
    }

    private boolean isValidSurname(List<User> lu, String surname){
        for(User e : lu){
            if (e.getSurname().equals(surname)){
                return true;
            }
        }
        return false;
    }


    public String addEmployee(){
        User u = this.getBySurname(this.inputEmployee);
        for(int i=1; i<7; i++){
            EmployeeDay e = new EmployeeDay(u, this.viewDate.withDayOfWeek(i));
            this.daoWorkingDay.createEmployeeDay(e);
        }
        this.inputEmployee = "";
        initEmployees();
        return "";
    }


    public String getWeekNumber(){
        return String.valueOf(this.viewDate.getWeekOfWeekyear());
    }

    public String getMonday(){
        LocalDate m = this.viewDate.withDayOfWeek(DateTimeConstants.MONDAY);
        return m.toString("dd/MM/yyyy");
    }

    public String getSaturday(){
        LocalDate s = this.viewDate.withDayOfWeek(DateTimeConstants.SATURDAY);
        return s.toString("dd/MM/yyyy");
    }

    public String removeEmployee(User e){
        for(WorkingDay wd : this.week){
            EmployeeDay ed = wd.getEmployeeDay(e);
            if(ed!= null){this.daoWorkingDay.deleteEmployeeDay(ed);}
        }
        initEmployees();
        return "";
    }

    public ArrayList<User> getWorkingEmployees() {
        return workingEmployees;
    }

    public void setWorkingEmployees(ArrayList<User> workingEmployees) {
        this.workingEmployees = workingEmployees;
    }

    public String getInputEmployee() {
        return inputEmployee;
    }

    public void setInputEmployee(String inputEmployee) {
        if(!isValidSurname(this.remainingEmployees, inputEmployee)){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur","Nom invalide");
            FacesContext.getCurrentInstance().addMessage("employeeNameMessage", message);
            this.inputEmployee="";
        }
        else{
            this.inputEmployee = inputEmployee;
        }
    }

    private ArrayList<WorkingDay> getCurrentWeek(){
        return this.daoWorkingDay.getWorkingDays(this.todayDate.withDayOfWeek(DateTimeConstants.MONDAY),
                this.todayDate.withDayOfWeek(DateTimeConstants.SATURDAY));
    }


    private ArrayList<WorkingDay> getWeek(LocalDate date){
        return this.daoWorkingDay.getWorkingDays(date.withDayOfWeek(DateTimeConstants.MONDAY),
                date.withDayOfWeek(DateTimeConstants.SATURDAY));
    }


    private ArrayList<User> generateWorkingEmployees(){
        HashSet<User> s = new HashSet<>();
        for(WorkingDay day : this.week){
            s.addAll(day.getWorkingEmployees());
        }
        ArrayList<User> al = new ArrayList<>();
        al.addAll(s);
        Collections.sort(al);
        return al;
    }


    public String hourNameFile(EmployeeDay ed, int hour){
        LocalTime time = new LocalTime(hour,30);
        if(ed.beginAt(time)){return "2";}
        if(ed.endAt(time)){return "3";}
        return "";
    }


    public boolean isRendered(EmployeeDay ed, int hour){
        LocalTime time = new LocalTime(hour,0);
        return ed.isWorkingBetween(time, time.plusHours(1));
    }


    public WorkingDay getDay(int day){
        try {
            WorkingDay d = null;
            d =this.week.get(day);
            return d;
        } catch (Exception e){
            return null;
        }

    }
}
