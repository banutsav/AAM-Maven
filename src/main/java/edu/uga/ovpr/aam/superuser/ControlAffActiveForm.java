/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.superuser;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class ControlAffActiveForm extends ValidatorForm {
    
    private String action = "";
    private String affId = "";
    private String active = "";
    private String firstName = "";
    private String lastName = "";
    private String ovprId = "";
    private String pseudoCan = "";
    private String organization = "";
    private String department = "";
    private String phone = "";
    private String email = "";
    private String title = "";
    private String targStatus = "";
    private String targPurposeId = "";
    private String targExpDate = "";
    private String targExpires = "";
    private ArrayList<Object> purposes = new ArrayList<Object>();

    public void reset(){
        action = "";
        affId = "";
        active = "";
        firstName = "";
        lastName = "";
        ovprId = "";
        pseudoCan = "";
        organization = "";
        department = "";
        phone = "";
        email = "";
        title = "";
        targStatus = "";
        targPurposeId = "";
        targExpDate = "";
        targExpires = "";
        purposes.clear();
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTargExpires() {
        return targExpires;
    }

    public void setTargExpires(String targExpires) {
        this.targExpires = targExpires;
    }

    public String getTargExpDate() {
        return targExpDate;
    }

    public void setTargExpDate(String targExpDate) {
        this.targExpDate = targExpDate;
    }

    public String getTargPurposeId() {
        return targPurposeId;
    }

    public void setTargPurposeId(String targPurposeId) {
        this.targPurposeId = targPurposeId;
    }

    public String getTargStatus() {
        return targStatus;
    }

    public void setTargStatus(String targStatus) {
        this.targStatus = targStatus;
    }

    public String getAffId() {
        return affId;
    }

    public void setAffId(String affId) {
        this.affId = affId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOvprId() {
        return ovprId;
    }

    public void setOvprId(String ovprId) {
        this.ovprId = ovprId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPseudoCan() {
        return pseudoCan;
    }

    public void setPseudoCan(String pseudoCan) {
        this.pseudoCan = pseudoCan;
    }

    public ArrayList<Object> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Object> purposes) {
        this.purposes = purposes;
    }
}
