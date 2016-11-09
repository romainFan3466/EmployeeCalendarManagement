package com.main.bean;

import com.main.dao.DAOUser;
import com.main.model.User;
import com.main.model.UserRole;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
//import javax.faces.view.*;
import java.io.Serializable;
import java.util.Collection;


@RequestScoped
@Named("employeeBean")
public class EmployeeBean implements Serializable {
    private static long serialVersionUID = 1L;
    @EJB
    private DAOUser daoUser = null;
    private User employee ;


    public EmployeeBean() {
        this.employee = new User();
        this.employee.setRole(UserRole.EMPLOYEE);

    }
    public void setEmployee(User employee) {
        this.employee = employee;
    }


    public User getEmployee() {
        return employee;
    }

    public Collection<User> getAllEmployee(){
        return  this.daoUser.getAllEmployee();
    }


    public void createEmployee(){
        daoUser.save(this.employee);
    }


}
