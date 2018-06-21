/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class ManCreateNewAffForm extends ValidatorForm {
    
    private String action = "";
    private String formType = "ManCreateNewAffForm";
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private String email = "";
    private String title = "";
    private String organization = "";
    private String department = "";
    private String pAreaCode = "";
    private String pPhone1 = "";
    private String pPhone2 = "";
    private String bAreaCode = "";
    private String bPhone1 = "";
    private String bPhone2 = "";
    private String aAreaCode = "";
    private String aPhone1 = "";
    private String aPhone2 = "";
    private String fAreaCode = "";
    private String fPhone1 = "";
    private String fPhone2 = "";
    private String inst_company = "";
    private String addr1 = "";
    private String addr2 = "";
    private String city = "";
    private String state = "";
    private String zip1 = "";
    private String zip2 = "";
    private String country = "";
    private String billing_group;
    private String billing_group_category = "";
    //private String expiration_dates = "";
    private String purpose_ids = "";
    private String purpose_periods = "";
    //private String purpose_period_counts = "";
    private String currAffEmails = "";
    private ArrayList<Object> purposes = new ArrayList<Object>();
    
    public void reset() {
        action = "";
        formType = "NewAffReqForm";
        firstName = "";
        middleName = "";
        lastName = "";
        email = "";
        title = "";
        organization = "";
        department = "";
        pAreaCode = "";
        pPhone1 = "";
        pPhone2 = "";
        bAreaCode = "";
        bPhone1 = "";
        bPhone2 = "";
        aAreaCode = "";
        aPhone1 = "";
        aPhone2 = "";
        fAreaCode = "";
        fPhone1 = "";
        fPhone2 = "";
        inst_company = "";
        addr1 = "";
        addr2 = "";
        city = "";
        state = "";
        zip1 = "";
        zip2 = "";
        country = "";
        billing_group = "";
        billing_group_category = "";
        purpose_ids = "";
        //expiration_dates = "";
        currAffEmails = "";
        purpose_ids = "";
        purpose_periods = "";
        //purpose_period_counts = "";
        purposes = new ArrayList<Object>();
    }

    public String getPurpose_periods() {
        return purpose_periods;
    }

    public void setPurpose_periods(String purpose_periods) {
        this.purpose_periods = purpose_periods;
    }

    public String getCurrAffEmails() {
        return currAffEmails;
    }

    public void setCurrAffEmails(String currAffEmails) {
        this.currAffEmails = currAffEmails;
    }

    public String getaAreaCode() {
        return aAreaCode;
    }

    public void setaAreaCode(String aAreaCode) {
        this.aAreaCode = aAreaCode;
    }

    public String getaPhone1() {
        return aPhone1;
    }

    public void setaPhone1(String aPhone1) {
        this.aPhone1 = aPhone1;
    }

    public String getaPhone2() {
        return aPhone2;
    }

    public void setaPhone2(String aPhone2) {
        this.aPhone2 = aPhone2;
    }

    public String getbAreaCode() {
        return bAreaCode;
    }

    public void setbAreaCode(String bAreaCode) {
        this.bAreaCode = bAreaCode;
    }

    public String getbPhone1() {
        return bPhone1;
    }

    public void setbPhone1(String bPhone1) {
        this.bPhone1 = bPhone1;
    }

    public String getbPhone2() {
        return bPhone2;
    }

    public void setbPhone2(String bPhone2) {
        this.bPhone2 = bPhone2;
    }

    public String getfAreaCode() {
        return fAreaCode;
    }

    public void setfAreaCode(String fAreaCode) {
        this.fAreaCode = fAreaCode;
    }

    public String getfPhone1() {
        return fPhone1;
    }

    public void setfPhone1(String fPhone1) {
        this.fPhone1 = fPhone1;
    }

    public String getfPhone2() {
        return fPhone2;
    }

    public void setfPhone2(String fPhone2) {
        this.fPhone2 = fPhone2;
    }

    public String getpAreaCode() {
        return pAreaCode;
    }

    public void setpAreaCode(String pAreaCode) {
        this.pAreaCode = pAreaCode;
    }

    public String getpPhone1() {
        return pPhone1;
    }

    public void setpPhone1(String pPhone1) {
        this.pPhone1 = pPhone1;
    }

    public String getpPhone2() {
        return pPhone2;
    }

    public void setpPhone2(String pPhone2) {
        this.pPhone2 = pPhone2;
    }

    public String getZip1() {
        return zip1;
    }

    public void setZip1(String zip1) {
        this.zip1 = zip1;
    }

    public String getZip2() {
        return zip2;
    }

    public void setZip2(String zip2) {
        this.zip2 = zip2;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getPurpose_ids() {
        return purpose_ids;
    }

    public void setPurpose_ids(String purpose_ids) {
        this.purpose_ids = purpose_ids;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBilling_group_category() {
        return billing_group_category;
    }

    public void setBilling_group_category(String billing_group_category) {
        this.billing_group_category = billing_group_category;
    }

    public String getBilling_group() {
        return billing_group;
    }

    public void setBilling_group(String billing_group) {
        this.billing_group = billing_group;
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

    public String getInst_company() {
        return inst_company;
    }

    public void setInst_company(String inst_company) {
        this.inst_company = inst_company;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public ArrayList<Object> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Object> purposes) {
        this.purposes = purposes;
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
}
