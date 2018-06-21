/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class ManPurposeForm extends ValidatorForm {
    private String action = ""; 
    private String purposeActive="0";
    private String purposeId = "";
    private ArrayList<Object> managerPurposes = new ArrayList<Object>();
    private ArrayList<Object> purposes = new ArrayList<Object>();

    public void reset() {
        action = "";
        purposeActive = "0";
        purposeId = "";
        managerPurposes = new ArrayList<Object>();
        purposes = new ArrayList<Object>();
    }

    public ArrayList<Object> getManagerPurposes() {
        return managerPurposes;
    }

    public void setManagerPurposes(ArrayList<Object> managerPurposes) {
        this.managerPurposes = managerPurposes;
    }

    public ArrayList<Object> getPurposes() {
        return purposes;
    }

    public void setPurposes(ArrayList<Object> purposes) {
        this.purposes = purposes;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getPurposeActive() {
        return purposeActive;
    }

    public void setPurposeActive(String purposeActive) {
        this.purposeActive = purposeActive;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
