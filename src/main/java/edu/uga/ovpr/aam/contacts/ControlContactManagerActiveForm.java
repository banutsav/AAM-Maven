/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

import edu.uga.ovpr.aam.superuser.*;
import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class ControlContactManagerActiveForm extends ValidatorForm {

    private String action = "";
    private String adminId = "";
    private String adminActive = "";
    private String targPurpId = "";
    private String targProxyId = "";
    private String firstName = "";
    private String lastName = "";
    private String myId = "";
    private String cannum = "";
    private String title = "";
    private String email = "";
    private String phone = "";
    private String deptName = "";
    private String deptNum = "";
    private ArrayList<Object> purposes = new ArrayList<Object>();
    private ArrayList<Object> proxies = new ArrayList<Object>();
    private ArrayList<Object> otherPurposes = new ArrayList<Object>();
    
    public void reset(){
        action = "";
        //adminId = "";
        adminActive = "";
        targPurpId = "";
        targProxyId = "";
        firstName = "";
        lastName = "";
        myId = "";
        cannum = "";
        title = "";
        email = "";
        phone = "";
        deptName = "";
        deptNum = "";
        purposes.clear();
        proxies.clear();
        otherPurposes.clear();
    }

    public String getAdminActive() {
        return adminActive;
    }

    public void setAdminActive(String adminActive) {
        this.adminActive = adminActive;
    }

    public String getTargProxyId() {
        return targProxyId;
    }

    public void setTargProxyId(String targProxyId) {
        this.targProxyId = targProxyId;
    }
    
    public ArrayList<Object> getOtherPurposes() {
        return otherPurposes;
    }

    public void setOtherPurposes(ArrayList<Object> otherPurposes) {
        this.otherPurposes = otherPurposes;
    }

    public String getTargPurpId() {
        return targPurpId;
    }

    public void setTargPurpId(String targPurpId) {
        this.targPurpId = targPurpId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<Object> getProxies() {
        return proxies;
    }

    public void setProxies(ArrayList<Object> proxies) {
        this.proxies = proxies;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void clearPurposes() {
        purposes.clear();
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getCannum() {
        return cannum;
    }

    public void setCannum(String cannum) {
        this.cannum = cannum;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}
