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
public class CreateContactsForm extends ValidatorForm{
    
    private String action = "";
    HashMap<String,Object> results = new HashMap<String,Object>();
    private String ovprId = "";
    private String lastName = "";
    private String lastNameOpt = "Begins";
    private String firstName = "";
    private String firstNameOpt = "Begins";
    private String ovprIdOpt = "Begins";
    private String org = "";
    private String orgOpt = "Begins";
    
    private String prime = "";
    
    // new contact selection
    private String targPersonCannum = "";
    
    private String executed = "";
    private Integer count = 0;
    private Integer pageNum = 0;
    
    private String displayPageNum = "";
    private String displayPageCount = "";
    
    public void reset(){
        results = new HashMap<String,Object>();
        ovprId = "";
        lastName = "";
        lastNameOpt = "Begins";
        firstName = "";
        org = "";
        firstNameOpt = "Begins";
        ovprIdOpt = "Begins";
        orgOpt = "Begins";
        targPersonCannum = "";
        executed = "";
        count = 0;
        pageNum = 0;
        displayPageNum = "";
        displayPageCount = "";
        
        prime = "";
    }
    public String getAction()
    {
        return action;
    }
    public void setAction(String action)
    {
        this.action = action;
    }
    
    public String getPrime()
    {
        return prime;
    }
    public void setPrime(String prime)
    {
        this.prime = prime;
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
    public String getOvprId()
    {
        return ovprId;
    }
    public void setOvprId(String ovprId)
    {
        this.ovprId = ovprId;
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
    public String getOvprIdOpt()
    {
        return ovprIdOpt;
    }
    public void setOvprIdOpt(String ovprIdOpt)
    {
        this.ovprIdOpt = ovprIdOpt;
    }
}
