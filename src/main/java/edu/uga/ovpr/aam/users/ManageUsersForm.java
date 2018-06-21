/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.users;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class ManageUsersForm extends ValidatorForm {
    private String action = "";
    private ArrayList<Object> currUsers = new ArrayList<Object>();

    public ArrayList<Object> getCurrUsers() {
        return currUsers;
    }

    public void setCurrUsers(ArrayList<Object> currUsers) {
        this.currUsers = currUsers;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
