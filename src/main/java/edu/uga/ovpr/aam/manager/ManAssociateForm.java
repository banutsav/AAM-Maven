/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class ManAssociateForm extends ValidatorForm {

    private String action = "";
    private String pseudoCan = "";
    private String assocId = "";
    private String purposeId = "";
    private String addPurposeId = "";
    private String addPurposeExpDate = "";
    private String targPurposeId = "";
    private String targExpDate = "";
    //private String periodCount = "";
    private String monthCount = "";
    private String periodLength = "";
    private String linkExpDate = "";
    private String back = "";
    private String backPurposeId = "";
    private String newApprovalStatus = "";
    private String expires = "";
    private HashMap<String, Object> associate = new HashMap<String, Object>();
    private ArrayList<Object> purposes = new ArrayList<Object>();
    
    private String isEnrolled = "";
    private String ugaHRStatus = "";

    public void reset() {
        action = "";
        assocId = "";
        pseudoCan = "";
        purposeId = "";
        addPurposeId = "";
        addPurposeExpDate = "";
        targPurposeId = "";
        targExpDate = "";
        //periodCount = "";
        monthCount = "";
        periodLength = "";
        linkExpDate = "";
        back = "";
        backPurposeId = "";
        newApprovalStatus = "";
        expires = "";
        associate = new HashMap<String, Object>();
        purposes = new ArrayList<Object>();
        
        isEnrolled = "";
        ugaHRStatus = "";
    }

     public String getisEnrolled()
    {
        return isEnrolled;
    }
    
    public void setisEnrolled(String isEnrolled)
    {
        this.isEnrolled = isEnrolled;
    }
    
    
    public String getugaHRStatus()
    {
        return ugaHRStatus;
    }
    
    public void setugaHRStatus(String ugaHRStatus)
    {
        this.ugaHRStatus = ugaHRStatus;
    }
    
     public String getassocId() {
        return assocId;
    }

    public void setassocId(String assocId) {
        this.assocId = assocId;
    }
    
    public String getBackPurposeId() {
        return backPurposeId;
    }

    public void setBackPurposeId(String backPurposeId) {
        this.backPurposeId = backPurposeId;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getNewApprovalStatus() {
        return newApprovalStatus;
    }

    public void setNewApprovalStatus(String newApprovalStatus) {
        this.newApprovalStatus = newApprovalStatus;
    }

    public String getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(String monthCount) {
        this.monthCount = monthCount;
    }

    public String getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(String periodLength) {
        this.periodLength = periodLength;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getLinkExpDate() {
        return linkExpDate;
    }

    public void setLinkExpDate(String linkExpDate) {
        this.linkExpDate = linkExpDate;
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

    public String getAddPurposeExpDate() {
        return addPurposeExpDate;
    }

    public void setAddPurposeExpDate(String addPurposeExpDate) {
        this.addPurposeExpDate = addPurposeExpDate;
    }

    public String getAddPurposeId() {
        return addPurposeId;
    }

    public void setAddPurposeId(String addPurposeId) {
        this.addPurposeId = addPurposeId;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getPseudoCan() {
        return pseudoCan;
    }

    public void setPseudoCan(String pseudoCan) {
        this.pseudoCan = pseudoCan;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HashMap<String, Object> getassociate() {
        return associate;
    }

    public void setassociate(HashMap<String, Object> associate) {
        this.associate = associate;
    }

    public ArrayList<Object> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Object> purposes) {
        this.purposes = purposes;
    }
}
