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
public class DefineProxyManagerForm extends ValidatorForm {
    private String action = "";
    private String targPurposeId = "";
    private String targPurposeShortName = "";
    private String targAdminId = "";
    private ArrayList<Object> purposes = new ArrayList<Object>();
    
    public void reset(){
        action = "";
        targPurposeId = "";
        targPurposeShortName = "";
        targAdminId = "";
        purposes = new ArrayList<Object>();
    }

    public String getTargPurposeShortName() {
        return targPurposeShortName;
    }

    public void setTargPurposeShortName(String targPurposeShortName) {
        this.targPurposeShortName = targPurposeShortName;
    }

    public String getTargAdminId() {
        return targAdminId;
    }

    public void setTargAdminId(String targAdminId) {
        this.targAdminId = targAdminId;
    }

    public String getTargPurposeId() {
        return targPurposeId;
    }

    public void setTargPurposeId(String targPurposeId) {
        this.targPurposeId = targPurposeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ArrayList<Object> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Object> purposes) {
        this.purposes = purposes;
    }

}
