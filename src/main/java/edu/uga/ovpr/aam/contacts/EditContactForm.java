/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uga.ovpr.aam.contacts;

import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author utsavb
 */
public class EditContactForm extends ValidatorForm{
    
    private String action = "";
    private String lastName = "";
    private String firstName = "";
    private String organization = "";
    private String email = "";
    private String active = "";
    private String addr1 = "";
    private String addr2 = "";
    private String city = "";
    private String state = "";
    private String zip = "";
    private String country = "";
    private String telephone = "";
    private String cellphone = "";
    private String title = "";
    private String fax = "";
    private String ugaid_prime = "0";
    
    // new contact selection
    private String targ_person = "0";
    
    private String targ_cannum = "0";
    
    public void reset(){
        lastName = "";
        firstName = "";
        organization = "";
        targ_person = "0";
        email = "";
        addr1 = "";
        addr2 = "";
        city = "";
        state = "";
        zip = "";
        country = "";
        telephone = "";
        cellphone = "";
        title = "";
        fax = "";
        ugaid_prime = "0";
   
    }
    public String getAction()
    {
        return action;
    }
    public void setAction(String action)
    {
        this.action = action;
    }
    
    public String getTarg_person() {
        return targ_person;
    }

    public void setTarg_person(String targ_person) {
        this.targ_person = targ_person;
    }
    
    public String getTarg_cannum() {
        return targ_cannum;
    }

    public void setTarg_cannum(String targ_cannum) {
        this.targ_cannum = targ_cannum;
    }
    
    public String getUgaid_prime()
    {
        return ugaid_prime;
    }
    public void setUgaid_prime(String ugaid_prime)
    {
        this.ugaid_prime = ugaid_prime;
    }
    
    public String getAddr1()
    {
        return addr1;
    }
    public void setAddr1(String addr1)
    {
        this.addr1 = addr1;
    }
    
    public String getAddr2()
    {
        return addr2;
    }
    public void setAddr2(String addr2)
    {
        this.addr2 = addr2;
    }
    
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    public String getZip()
    {
        return zip;
    }
    public void setZip(String zip)
    {
        this.zip = zip;
    }
    public String getActive()
    {
        return active;
    }
    public void setActive(String active)
    {
        this.active = active;
    }
    public String getFax()
    {
        return fax;
    }
    public void setFax(String fax)
    {
        this.fax = fax;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    public String getTelephone()
    {
        return telephone;
    }
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }
    public String getCellphone()
    {
        return cellphone;
    }
    public void setCellphone(String cellphone)
    {
        this.cellphone = cellphone;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
     public String getOrganization()
    {
        return organization;
    }
    public void setOrganization(String organization)
    {
        this.organization = organization;
    }
}
