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
public class ControlAffiliatesForm extends ValidatorForm {
    
    private String action = "";
    private String executed = "";
    private String affId = "";
    // Variables used to define the query
    private String firstName;
    private String lastName;
    private String ovprId;
    private String pseudoCan;
    private String jobTitle;
    private String deptName;
    private String orgName;
    private String firstNameOpt = "Begins";
    private String lastNameOpt = "Begins";
    private String ovprIdOpt = "Contains";
    private String pseudoCanOpt = "Exact";
    private String jobTitleOpt = "Contains";
    private String deptNameOpt = "Contains";
    private String orgNameOpt = "Begins";
    private String incInactive = "No";
    // Variables used to define the range of query results viewed
    private Integer count = 0;
    private Integer pageNum = 0;
    private String displayPageNum = "";
    private String displayPageCount = "";
    private String orderBy;
    private String changePageOpt;
    private String purposeId;
    private String purposeShortName;
    private String managerCannum;
    private String newProxyManagerFullName = "";
    //private ArrayList<Object> queryResults = new ArrayList<Object>();

    public void reset() {
        this.action = "";
        this.executed = "";
        this.affId = "";
        this.firstName = "";
        this.lastName = "";
        this.ovprId = "";
        this.pseudoCan = "";
        this.jobTitle = "";
        this.deptName = "";
        this.orgName = "";
        this.firstNameOpt = "Begins";
        this.lastNameOpt = "Begins";
        this.ovprIdOpt = "Contains";
        this.pseudoCanOpt = "Exact";
        this.jobTitleOpt = "Contains";
        this.deptNameOpt = "Contains";
        this.deptNameOpt = "Begins";
        this.incInactive = "No";
        this.count = 0;
        this.pageNum = 0;
        this.displayPageNum = "";
        this.displayPageCount = "";
        this.orderBy = "";
        this.changePageOpt = "";
        this.newProxyManagerFullName = "";
        //this.purposeId = "";
        //this.purposeShortName = "";
        //this.managerCannum = "";
        //this.queryResults.clear();
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
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

    public String getAffId() {
        return affId;
    }

    public void setAffId(String affId) {
        this.affId = affId;
    }

    public String getOvprIdOpt() {
        return ovprIdOpt;
    }

    public void setOvprIdOpt(String ovprIdOpt) {
        this.ovprIdOpt = ovprIdOpt;
    }

    public String getPseudoCanOpt() {
        return pseudoCanOpt;
    }

    public void setPseudoCanOpt(String pseudoCanOpt) {
        this.pseudoCanOpt = pseudoCanOpt;
    }

    public String getOvprId() {
        return ovprId;
    }

    public void setOvprId(String ovprId) {
        this.ovprId = ovprId;
    }

    public String getPseudoCan() {
        return pseudoCan;
    }

    public void setPseudoCan(String pseudoCan) {
        this.pseudoCan = pseudoCan;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChangePageOpt() {
        return changePageOpt;
    }

    public void setChangePageOpt(String changePageOpt) {
        this.changePageOpt = changePageOpt;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
    
    public String getIncInactive() {
        return incInactive;
    }

    public void setIncInactive(String incInactive) {
        this.incInactive = incInactive;
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

    public String getManagerCannum() {
        return managerCannum;
    }

    public void setManagerCannum(String managerCannum) {
        this.managerCannum = managerCannum;
    }
    
    public String getNewProxyManagerFullName() {
        return newProxyManagerFullName;
    }

    public void setNewProxyManagerFullName(String newProxyManagerFullName) {
        this.newProxyManagerFullName = newProxyManagerFullName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getPurposeShortName() {
        return purposeShortName;
    }

    public void setPurposeShortName(String purposeShortName) {
        this.purposeShortName = purposeShortName;
    }
}
