/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author programmer
 */
public class AddAffManagerForm extends ValidatorForm {

    private String appName = "";
    private String appId = "";
    private String submitOpt;
    // Variables used to define the query
    private String firstName;
    private String lastName;
    private String myId;
    private String ugaId;
    private String jobTitle;
    private String homeDeptNum;
    private String homeDeptName;
    private String firstNameOpt = "Begins";
    private String lastNameOpt = "Begins";
    private String myIdOpt = "Contains";
    private String ugaIdOpt = "Exact";
    private String jobTitleOpt = "Contains";
    private String homeDeptNumOpt = "Begins";
    private String homeDeptNameOpt = "Contains";
    private String incAffiliates = "No";
    private String incInactive = "No";
    // Variables used to define the range of query results viewed
    private Integer count = 0;
    private Integer pageNum = 0;
    private String orderBy;
    private String changePageOpt;
    private String purposeId;
    private String purposeShortName;
    private String managerCannum;
    private String newProxyManagerFullName = "";
    private ArrayList<Object> queryResults;
    private ArrayList<Object> purposes;

    public void resetQuery() {
        this.submitOpt = "";
        this.firstName = "";
        this.lastName = "";
        this.myId = "";
        this.ugaId = "";
        this.jobTitle = "";
        this.homeDeptNum = "";
        this.homeDeptName = "";
        this.firstNameOpt = "Begins";
        this.lastNameOpt = "Begins";
        this.myIdOpt = "Contains";
        this.ugaIdOpt = "Exact";
        this.jobTitleOpt = "Contains";
        this.homeDeptNumOpt = "Begins";
        this.homeDeptNameOpt = "Contains";
        this.incAffiliates = "No";
        this.incInactive = "No";
        this.count = 0;
        this.pageNum = 0;
        this.orderBy = "";
        this.changePageOpt = "";
        this.newProxyManagerFullName = "";
        //this.purposeId = "";
        //this.purposeShortName = "";
        //this.managerCannum = "";
        this.queryResults = new ArrayList<Object>();
        this.purposes = new ArrayList<Object>();
    }

    public String getNewProxyManagerFullName() {
        return newProxyManagerFullName;
    }

    public void setNewProxyManagerFullName(String newProxyManagerFullName) {
        this.newProxyManagerFullName = newProxyManagerFullName;
    }

    public String getPurposeShortName() {
        return purposeShortName;
    }

    public void setPurposeShortName(String purposeShortName) {
        this.purposeShortName = purposeShortName;
    }

    public String getManagerCannum() {
        return managerCannum;
    }

    public void setManagerCannum(String managerCannum) {
        this.managerCannum = managerCannum;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public ArrayList<Object> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Object> purposes) {
        this.purposes = purposes;
    }

    public void reset(){
        this.resetQuery();
        this.appId = "";
        this.appName = "";
    }
    
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<Object> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(ArrayList<Object> queryResults) {
        this.queryResults = queryResults;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getChangePageOpt() {
        return changePageOpt;
    }

    public void setChangePageOpt(String changePageOpt) {
        this.changePageOpt = changePageOpt;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getIncAffiliates() {
        return incAffiliates;
    }

    public void setIncAffiliates(String incAffiliates) {
        this.incAffiliates = incAffiliates;
    }

    public String getIncInactive() {
        return incInactive;
    }

    public void setIncInactive(String incInactive) {
        this.incInactive = incInactive;
    }

    public String getFirstNameOpt() {
        return firstNameOpt;
    }

    public void setFirstNameOpt(String firstNameOpt) {
        this.firstNameOpt = firstNameOpt;
    }

    public String getHomeDeptNameOpt() {
        return homeDeptNameOpt;
    }

    public void setHomeDeptNameOpt(String homeDeptNameOpt) {
        this.homeDeptNameOpt = homeDeptNameOpt;
    }

    public String getHomeDeptNumOpt() {
        return homeDeptNumOpt;
    }

    public void setHomeDeptNumOpt(String homeDeptNumOpt) {
        this.homeDeptNumOpt = homeDeptNumOpt;
    }

    public String getJobTitleOpt() {
        return jobTitleOpt;
    }

    public void setJobTitleOpt(String jobTitleOpt) {
        this.jobTitleOpt = jobTitleOpt;
    }

    public String getLastNameOpt() {
        return lastNameOpt;
    }

    public void setLastNameOpt(String lastNameOpt) {
        this.lastNameOpt = lastNameOpt;
    }

    public String getMyIdOpt() {
        return myIdOpt;
    }

    public void setMyIdOpt(String myIdOpt) {
        this.myIdOpt = myIdOpt;
    }

    public String getUgaIdOpt() {
        return ugaIdOpt;
    }

    public void setUgaIdOpt(String ugaIdOpt) {
        this.ugaIdOpt = ugaIdOpt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHomeDeptName() {
        return homeDeptName;
    }

    public void setHomeDeptName(String homeDeptName) {
        this.homeDeptName = homeDeptName;
    }

    public String getHomeDeptNum() {
        return homeDeptNum;
    }

    public void setHomeDeptNum(String homeDeptNum) {
        this.homeDeptNum = homeDeptNum;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getUgaId() {
        return ugaId;
    }

    public void setUgaId(String ugaId) {
        this.ugaId = ugaId;
    }

    public String getSubmitOpt() {
        return submitOpt;
    }

    public void setSubmitOpt(String submitOpt) {
        this.submitOpt = submitOpt;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
