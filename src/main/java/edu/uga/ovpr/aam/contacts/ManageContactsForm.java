/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uga.ovpr.aam.contacts;

import java.util.HashMap;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author utsavb
 */
public class ManageContactsForm extends ValidatorForm{
    
    private String action = "";
    HashMap<String,Object> results = new HashMap<String,Object>();
    private String lastName = "";
    private String lastNameOpt = "Begins";
    private String firstName = "";
    private String firstNameOpt = "Begins";
    private String org = "";
    private String orgOpt = "Begins";
    private String executed = "";
    private String targPersonCannum = "";
    
    private String showInactive = "No";
    
    private Integer pageNum = 0;
    private String displayPageNum = "";
    private Integer count = 0;
    private String displayPageCount = "";
    
    public void reset(){
        results = new HashMap<String,Object>();
        lastName = "";
        lastNameOpt = "Begins";
        firstName = "";
        firstNameOpt = "Begins";
        org = "";
        orgOpt = "Begins";
        executed = "";
        
        pageNum = 0;
        displayPageNum = "";
        targPersonCannum = "";
        count = 0;
        displayPageCount = "";
        showInactive = "No";
    }
    public String getAction()
    {
        return action;
    }
    public void setAction(String action)
    {
        this.action = action;
    }
    
    public String getShowInactive()
    {
        return showInactive;
    }
    public void setShowInactive(String showInactive)
    {
        this.showInactive = showInactive;
    }
    
    public String getExecuted() {
        return executed;
    }
    public void setExecuted(String executed) {
        this.executed = executed;
    }
    public String getTargPersonCannum() {
        return targPersonCannum;
    }
    public void setTargPersonCannum(String targPersonCannum) {
        this.targPersonCannum = targPersonCannum;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
     public String getLastNameOpt()
    {
        return lastNameOpt;
    }
    public void setLastNameOpt(String lastNameOpt)
    {
        this.lastNameOpt = lastNameOpt;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getFirstNameOpt()
    {
        return firstNameOpt;
    }
    public void setFirstNameOpt(String firstNameOpt)
    {
        this.firstNameOpt = firstNameOpt;
    }
    public String getOrg()
    {
        return org;
    }
    public void setOrg(String org)
    {
        this.org = org;
    }
    public String getOrgOpt()
    {
        return orgOpt;
    }
    public void setOrgOpt(String orgOpt)
    {
        this.orgOpt = orgOpt;
    }
    public String getDisplayPageNum() {
        return displayPageNum;
    }

    public void setDisplayPageNum(String displayPageNum) {
        this.displayPageNum = displayPageNum;
    }
    
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
    public String getDisplayPageCount() {
        return displayPageCount;
    }

    public void setDisplayPageCount(String displayPageCount) {
        this.displayPageCount = displayPageCount;
    }
}
