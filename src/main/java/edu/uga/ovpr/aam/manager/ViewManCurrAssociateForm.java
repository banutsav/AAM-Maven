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
public class ViewManCurrAssociateForm extends ValidatorForm {

    private String action = "";
    //private ArrayList<Object> queryResults = new ArrayList<Object>();
    private ArrayList<Object> purposes = new ArrayList<Object>();
    private String firstName = "";
    private String firstNameOpt = "begins";
    private String lastName = "";
    private String lastNameOpt = "begins";
    private String ovprId = "";
    private String ovprIdOpt = "exact";
    private String pseudoCan = "";
    private String pseudoCanOpt = "exact";
    private String status = "";
    private String organization = "";
    private String department = "";
    private String email = "";
    private String emailOpt = "begins";
    private String phone = "";
    private String organizationCompany = "";
    private String organizationCompanyOpt = "begins";
    private String manFirstName = "";
    private String manFirstNameOpt = "begins";
    private String manLastName = "";
    private String manLastNameOpt = "begins";
    private String manDeptName = "";
    private String manDeptNameOpt = "begins";
    private String manDeptNum = "";
    private String manDeptNumOpt = "exact";
    private String manMyId = "";
    private String manMyIdOpt = "exact";
    private String manUgaId = "";
    private String manUgaIdOpt = "exact";
    private String purposeIds = "";
    private String showInactive = "Yes";
    private String showExpired = "No";
    private String showExpire = "No";
    private String purposeManageFilter = "Yes";
    private String approvedBefore = "";
    private String approvedAfter = "";
    private String executed = "";
    private String targUserId = "";
    private String targPurposeId = "";
    private String targApproved = "";
    private String targActive = "";
    private String targExpDate = "";
    private String addPurpUserId = "";
    private String addPurpPurposeId = "";
    private String addPurpApproved = "";
    private String addPurpActive = "";
    private String addPurpExpDate = "";
    private String manOptOpen = "0";
    private String purposeOptOpen = "0";
    private String isEnrolled = "";
    private String ugaHRStatus = "";
    

    public void reset() {
        action = "";
        //queryResults = new ArrayList<Object>();
        purposes = new ArrayList<Object>();
        firstName = "";
        firstNameOpt = "begins";
        lastName = "";
        lastNameOpt = "begins";
        ovprId = "";
        ovprIdOpt = "exact";
        pseudoCan = "";
        pseudoCanOpt = "exact";
        status = "";
        email = "";
        emailOpt = "begins";
        phone = "";
        organizationCompany = "";
        organizationCompanyOpt = "begins";
        approvedBefore = "";
        approvedAfter = "";
        manFirstName = "";
        manFirstNameOpt = "begins";
        manLastName = "";
        manLastNameOpt = "begins";
        manDeptName = "";
        manDeptNameOpt = "begins";
        manDeptNum = "";
        manDeptNumOpt = "exact";
        manMyId = "";
        manMyIdOpt = "exact";
        manUgaId = "";
        manUgaIdOpt = "exact";
        purposeIds = "";
        showInactive = "Yes";
        showExpired = "No";
        showExpire = "No";
        purposeManageFilter = "Yes";
        executed = "";
        targUserId = "";
        targPurposeId = "";
        targApproved = "";
        targActive = "";
        targExpDate = "";
        addPurpUserId = "";
        addPurpPurposeId = "";
        addPurpApproved = "";
        addPurpActive = "";
        addPurpExpDate = "";
        manOptOpen = "0";
        purposeOptOpen = "0";
        
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
    
    
    public String getShowExpired() {
        return showExpired;
    }

    public void setShowExpired(String showExpired) {
        this.showExpired = showExpired;
    }

    public String getPurposeManageFilter() {
        return purposeManageFilter;
    }

    public void setPurposeManageFilter(String purposeManageFilter) {
        this.purposeManageFilter = purposeManageFilter;
    }

    public String getShowExpire() {
        return showExpire;
    }

    public void setShowExpire(String showExpire) {
        this.showExpire = showExpire;
    }

    public String getAddPurpActive() {
        return addPurpActive;
    }

    public void setAddPurpActive(String addPurpActive) {
        this.addPurpActive = addPurpActive;
    }

    public String getAddPurpApproved() {
        return addPurpApproved;
    }

    public void setAddPurpApproved(String addPurpApproved) {
        this.addPurpApproved = addPurpApproved;
    }

    public String getAddPurpExpDate() {
        return addPurpExpDate;
    }

    public void setAddPurpExpDate(String addPurpExpDate) {
        this.addPurpExpDate = addPurpExpDate;
    }

    public String getAddPurpPurposeId() {
        return addPurpPurposeId;
    }

    public void setAddPurpPurposeId(String addPurpPurposeId) {
        this.addPurpPurposeId = addPurpPurposeId;
    }

    public String getAddPurpUserId() {
        return addPurpUserId;
    }

    public void setAddPurpUserId(String addPurpUserId) {
        this.addPurpUserId = addPurpUserId;
    }

    public String getTargActive() {
        return targActive;
    }

    public void setTargActive(String targActive) {
        this.targActive = targActive;
    }

    public String getTargApproved() {
        return targApproved;
    }

    public void setTargApproved(String targApproved) {
        this.targApproved = targApproved;
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

    public String getTargUserId() {
        return targUserId;
    }

    public void setTargUserId(String targUserId) {
        this.targUserId = targUserId;
    }

    public String getManOptOpen() {
        return manOptOpen;
    }

    public void setManOptOpen(String manOptOpen) {
        this.manOptOpen = manOptOpen;
    }

    public String getPurposeOptOpen() {
        return purposeOptOpen;
    }

    public void setPurposeOptOpen(String purposeOptOpen) {
        this.purposeOptOpen = purposeOptOpen;
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }

    public String getManDeptNumOpt() {
        return manDeptNumOpt;
    }

    public void setManDeptNumOpt(String manDeptNumOpt) {
        this.manDeptNumOpt = manDeptNumOpt;
    }

    public String getManMyIdOpt() {
        return manMyIdOpt;
    }

    public void setManMyIdOpt(String manMyIdOpt) {
        this.manMyIdOpt = manMyIdOpt;
    }

    public String getManUgaIdOpt() {
        return manUgaIdOpt;
    }

    public void setManUgaIdOpt(String manUgaIdOpt) {
        this.manUgaIdOpt = manUgaIdOpt;
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

    public String getEmailOpt() {
        return emailOpt;
    }

    public void setEmailOpt(String emailOpt) {
        this.emailOpt = emailOpt;
    }

    public String getFirstNameOpt() {
        return firstNameOpt;
    }

    public void setFirstNameOpt(String firstNameOpt) {
        this.firstNameOpt = firstNameOpt;
    }

    public String getLastNameOpt() {
        return lastNameOpt;
    }

    public void setLastNameOpt(String lastNameOpt) {
        this.lastNameOpt = lastNameOpt;
    }

    public String getManDeptNameOpt() {
        return manDeptNameOpt;
    }

    public void setManDeptNameOpt(String manDeptNameOpt) {
        this.manDeptNameOpt = manDeptNameOpt;
    }

    public String getManFirstNameOpt() {
        return manFirstNameOpt;
    }

    public void setManFirstNameOpt(String manFirstNameOpt) {
        this.manFirstNameOpt = manFirstNameOpt;
    }

    public String getManLastNameOpt() {
        return manLastNameOpt;
    }

    public void setManLastNameOpt(String manLastNameOpt) {
        this.manLastNameOpt = manLastNameOpt;
    }

    public String getPurposeIds() {
        return purposeIds;
    }

    public void setPurposeIds(String purposeIds) {
        this.purposeIds = purposeIds;
    }

    public ArrayList<Object> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Object> purposes) {
        this.purposes = purposes;
    }

    public String getApprovedAfter() {
        return approvedAfter;
    }

    public void setApprovedAfter(String approvedAfter) {
        this.approvedAfter = approvedAfter;
    }

    public String getApprovedBefore() {
        return approvedBefore;
    }

    public void setApprovedBefore(String approvedBefore) {
        this.approvedBefore = approvedBefore;
    }

    public String getManDeptNum() {
        return manDeptNum;
    }

    public void setManDeptNum(String manDeptNum) {
        this.manDeptNum = manDeptNum;
    }

    public String getManDeptName() {
        return manDeptName;
    }

    public void setManDeptName(String manDeptName) {
        this.manDeptName = manDeptName;
    }

    public String getShowInactive() {
        return showInactive;
    }

    public void setShowInactive(String showInactive) {
        this.showInactive = showInactive;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getOrganizationCompany() {
        return organizationCompany;
    }

    public void setOrganizationCompany(String organizationCompany) {
        this.organizationCompany = organizationCompany;
    }

    public String getOrganizationCompanyOpt() {
        return organizationCompanyOpt;
    }

    public void setOrganizationCompanyOpt(String organizationCompanyOpt) {
        this.organizationCompanyOpt = organizationCompanyOpt;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getManFirstName() {
        return manFirstName;
    }

    public void setManFirstName(String manFirstName) {
        this.manFirstName = manFirstName;
    }

    public String getManLastName() {
        return manLastName;
    }

    public void setManLastName(String manLastName) {
        this.manLastName = manLastName;
    }

    public String getManMyId() {
        return manMyId;
    }

    public void setManMyId(String manMyId) {
        this.manMyId = manMyId;
    }

    public String getManUgaId() {
        return manUgaId;
    }

    public void setManUgaId(String manUgaId) {
        this.manUgaId = manUgaId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOvprId() {
        return ovprId;
    }

    public void setOvprId(String ovprId) {
        this.ovprId = ovprId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPseudoCan() {
        return pseudoCan;
    }

    public void setPseudoCan(String pseudoCan) {
        this.pseudoCan = pseudoCan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
