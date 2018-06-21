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
public class ControlPurposesForm extends ValidatorForm {

    private String action = "";
    private String executed = "";
    private String shortName = "";
    private String targShortName = "";
    private String description = "";
    private String newShortName = "";
    private String newDescription = "";
    private String newApproved = "";
    private String newPeriod = "";
    private String shortNameOpt = "Begins";
    private String descriptionOpt = "Begins";
    private String incInactive = "No";
    private String purposeId = "";
    private Integer count = 0;
    private Integer pageNum = 0;
    private String displayPageNum = "";
    private String displayPageCount = "";
    private String orderBy;
    private String changePageOpt;
    //private ArrayList<Object> purposes = new ArrayList<Object>();

    public void reset() {
        action = "";
        executed = "";
        shortName = "";
        targShortName = "";
        description = "";
        newShortName = "";
        newDescription = "";
        newApproved = "";
        newPeriod = "";
        shortNameOpt = "Begins";
        descriptionOpt = "Begins";
        incInactive = "No";
        purposeId = "";
        count = 0;
        pageNum = 0;
        displayPageNum = "";
        displayPageCount = "";
        //purposes = new ArrayList<Object>();
    }

    public String getTargShortName() {
        return targShortName;
    }

    public void setTargShortName(String targShortName) {
        this.targShortName = targShortName;
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }

    public String getNewApproved() {
        return newApproved;
    }

    public void setNewApproved(String newApproved) {
        this.newApproved = newApproved;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public String getNewPeriod() {
        return newPeriod;
    }

    public void setNewPeriod(String newPeriod) {
        this.newPeriod = newPeriod;
    }

    public String getNewShortName() {
        return newShortName;
    }

    public void setNewShortName(String newShortName) {
        this.newShortName = newShortName;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionOpt() {
        return descriptionOpt;
    }

    public void setDescriptionOpt(String descriptionOpt) {
        this.descriptionOpt = descriptionOpt;
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

    public String getIncInactive() {
        return incInactive;
    }

    public void setIncInactive(String incInactive) {
        this.incInactive = incInactive;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortNameOpt() {
        return shortNameOpt;
    }

    public void setShortNameOpt(String shortNameOpt) {
        this.shortNameOpt = shortNameOpt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
