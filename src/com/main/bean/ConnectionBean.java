package com.main.bean;

import com.main.dao.DAOUser;
import com.main.dao.exception.DAOException;
import com.main.exception.InvalidFieldException;
import com.main.model.User;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by romain on 04/10/16.
 */

@Named
@SessionScoped
public class ConnectionBean implements Serializable {
    private static long serialVersionUID = 1L;

    private User user;

    @EJB
    private DAOUser daoUser = null;

    private boolean loggedIn = false;
    private boolean error = false;


    public ConnectionBean() {
        this.user = new User();
    }


    public String connectUser() {

        User u = null;
        try {
            u = this.daoUser.get(user);
        } catch (DAOException e) {
            FacesMessage message = new FacesMessage("Problem DAO");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        if (u != null) {
            ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
            passwordEncryptor.setAlgorithm("SHA-256");
            passwordEncryptor.setPlainDigest(true);

            if (passwordEncryptor.checkPassword(this.user.getPassword(), u.getPassword())) {
                this.user = u;
                this.loggedIn = true;
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                try{
                    context.redirect(context.getRequestContextPath() + "/home.xhtml");
                } catch (Exception e){

                }

            } else {
                FacesMessage message = new FacesMessage("Wrong credentials");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            this.error = true;
            FacesMessage message = new FacesMessage("Wrong cred");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    public boolean getError() {
        return this.error;
    }

    public boolean isLoggedIn(){
        return this.loggedIn;
    }

    public User getUser() {
        return this.user;
    }


}
