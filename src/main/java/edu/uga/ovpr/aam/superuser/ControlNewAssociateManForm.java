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
public class ControlNewAssociateManForm extends ValidatorForm {

    private String action = "";
    // Variables used to define the query
    private String firstName;
    private String lastName;
    private String myId;
    private String cannum;
    private String firstNameOpt = "Begins";
    private String lastNameOpt = "Begins";
    private String myIdOpt = "Contains";
    private String cannumOpt = "Exact";
    private String incInactive = "No";
    private String targPersonCannum = "";
    // Variables used to define the range of query results viewed
    private Integer count = 0;
    private Integer pageNum = 0;
    private String displayPageNum = "";
    private String displayPageCount = "";
    private String orderBy;
    private String changePageOpt;
    private ArrayList<Object> queryResults = new ArrayList<Object>();

    public void reset() {
        action = "";
        firstNameOpt = "Begins";
        lastNameOpt = "Begins";
        myIdOpt = "Contains";
        cannumOpt = "Exact";
        incInactive = "No";
        count = 0;
        pageNum = 0;
        displayPageNum = "";
        displayPageCount = "";
        queryResults = new ArrayList<Object>();
        targPersonCannum = "";
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

    public ArrayList<Object> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(ArrayList<Object> queryResults) {
        this.queryResults = queryResults;
    }

    public String getCannum() {
        return cannum;
    }

    public void setCannum(String cannum) {
        this.cannum = cannum;
    }

    public String getCannumOpt() {
        return cannumOpt;
    }

    public void setCannumOpt(String cannumOpt) {
        this.cannumOpt = cannumOpt;
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

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getMyIdOpt() {
        return myIdOpt;
    }

    public void setMyIdOpt(String myIdOpt) {
        this.myIdOpt = myIdOpt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargPersonCannum() {
        return targPersonCannum;
    }

    public void setTargPersonCannum(String targPersonCannum) {
        this.targPersonCannum = targPersonCannum;
    }
}
