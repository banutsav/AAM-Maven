/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class GenFindAffForm extends ValidatorForm {

    private String action = "";
    private String targAffId = "";
    private String targPurpId = "";
    private String email = "";
    private String emailOpt = "Contains";
    private String lastName = "";
    private String lastNameOpt = "Begins";
    private String firstName = "";
    private String firstNameOpt = "Begins";
    private String pseudoCan = "";
    private String pseudoCanOpt = "Exact";
    private String ovprId = "";
    private String ovprIdOpt = "Contains";
    private String jobTitle = "";
    private String jobTitleOpt = "Begins";
    private String orgName = "";
    private String orgNameOpt = "Begins";
    private String deptName = "";
    private String deptNameOpt = "Begins";
    private Integer count = -1;
    private Integer pageNum = 0;
    private String executed = "";
    //HashMap<String, Object> result = new HashMap<String, Object>();
    ArrayList<Object> results = new ArrayList<Object>();

    public void reset() {
        action = "";
        targAffId = "";
        targPurpId = "";
        email = "";
        emailOpt = "Contains";
        lastName = "";
        lastNameOpt = "Begins";
        firstName = "";
        firstNameOpt = "Begins";
        pseudoCan = "";
        pseudoCanOpt = "Exact";
        ovprId = "";
        ovprIdOpt = "Contains";
        jobTitle = "";
        jobTitleOpt = "Begins";
        orgName = "";
        orgNameOpt = "Begins";
        deptName = "";
        deptNameOpt = "Begins";
        count = -1;
        pageNum = 0;
        executed = "";
        //result = new HashMap<String, Object>();
        //results = new ArrayList<Object>();

    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNameOpt() {
        return deptNameOpt;
    }

    public void setDeptNameOpt(String deptNameOpt) {
        this.deptNameOpt = deptNameOpt;
    }

    public String getEmailOpt() {
        return emailOpt;
    }

    public void setEmailOpt(String emailOpt) {
        this.emailOpt = emailOpt;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public ArrayList<Object> getResults() {
        return results;
    }

    public void setResults(ArrayList<Object> results) {
        this.results = results;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNameOpt() {
        return firstNameOpt;
    }

    public void setFirstNameOpt(String firstNameOpt) {
        this.firstNameOpt = firstNameOpt;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitleOpt() {
        return jobTitleOpt;
    }

    public void setJobTitleOpt(String jobTitleOpt) {
        this.jobTitleOpt = jobTitleOpt;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNameOpt() {
        return lastNameOpt;
    }

    public void setLastNameOpt(String lastNameOpt) {
        this.lastNameOpt = lastNameOpt;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNameOpt() {
        return orgNameOpt;
    }

    public void setOrgNameOpt(String orgNameOpt) {
        this.orgNameOpt = orgNameOpt;
    }

    public String getOvprId() {
        return ovprId;
    }

    public void setOvprId(String ovprId) {
        this.ovprId = ovprId;
    }

    public String getOvprIdOpt() {
        return ovprIdOpt;
    }

    public void setOvprIdOpt(String ovprIdOpt) {
        this.ovprIdOpt = ovprIdOpt;
    }

    public String getPseudoCan() {
        return pseudoCan;
    }

    public void setPseudoCan(String pseudoCan) {
        this.pseudoCan = pseudoCan;
    }

    public String getPseudoCanOpt() {
        return pseudoCanOpt;
    }

    public void setPseudoCanOpt(String pseudoCanOpt) {
        this.pseudoCanOpt = pseudoCanOpt;
    }

    public String getTargPurpId() {
        return targPurpId;
    }

    public void setTargPurpId(String targPurpId) {
        this.targPurpId = targPurpId;
    }

    public String getTargAffId() {
        return targAffId;
    }

    public void setTargAffId(String targAffId) {
        this.targAffId = targAffId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
