package com.main.dao;

import com.main.dao.exception.DAOException;
import com.main.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.ejb.*;
import java.util.Collection;


@Stateless
public class DAOUser  {

    private static final String JPQL_QUERY = "SELECT u FROM User u WHERE u.name=:name";

    @PersistenceContext(unitName = "calendarEEPU")
    private EntityManager em;

    public User get(User  u) throws DAOException{
        User user = null;

        try{
            Query req = this.em.createQuery(JPQL_QUERY);
            req.setParameter("name", u.getName());
            user = (User) req.getSingleResult();
        }catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return user;
    }

    public Collection<User> getAllEmployee() throws DAOException{
        Collection<User> users = null;
        String JPQL= "SELECT u FROM User u WHERE u.role='EMPLOYEE'";
        try{
            Query req = this.em.createQuery(JPQL);
            users = (Collection<User>) req.getResultList();
        }catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return users;
    }


    public void save(User u){
        if (isExisting(u)){
            FacesMessage message  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur","l' utilisateur " + u.getName() +" existe déja");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else {
            try{
                em.persist(u);
            }catch (Exception e){
                FacesMessage message  = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur enregistrement employé");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            FacesMessage message  = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes", "l' utilisateur " + u.getName() +"a bien été ajouté");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public boolean isExisting(User u){
        String SQL_QUERY = "SELECT u FROM User u WHERE u.name=:name AND u.surname=:surname";
        try{
            Query req = this.em.createQuery(SQL_QUERY);
            req.setParameter("name", u.getName());
            req.setParameter("surname", u.getSurname());
            User res = (User) req.getSingleResult();
            return res != null;
        }catch ( NoResultException e ) {
            return false;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }


    public void update(User u){
        try{
            em.merge(u);
        } catch (IllegalArgumentException e){
            FacesMessage message  = new FacesMessage("l' utilisateur" + u.getName() +" n'existe pas, ou impossible de sauvegarder");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
