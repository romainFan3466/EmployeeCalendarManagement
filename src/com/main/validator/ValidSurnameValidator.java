package com.main.validator;

import com.main.model.User;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@FacesValidator( value = "validSurnameValidator" )
public class ValidSurnameValidator implements Validator {



    public ValidSurnameValidator(){
    }

    private boolean isValidSurname(List<User> lu, String surname){
        for(User e : lu){
            if (e.getSurname().equals(surname)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {

//        Map<String, Object> componentList = component.getAttributes();
        ArrayList<User> l = (ArrayList<User>) component.getAttributes().get("list");

        String s =" ";
        if(!this.isValidSurname(l, (String)value)){
            FacesMessage msg =
                    new FacesMessage("E-mail validation failed.",
                            "Invalid E-mail format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }


    }

}
