/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class PurposeDataForm extends ValidatorForm {
    private String action = "";
    private String purposeId = "";
    private String targAffId = "";
    private HashMap<String,Object> purposeData = new HashMap<String,Object>();
    private ArrayList<Object> purposeAff = new ArrayList<Object>();
    private ArrayList<Object> purposeManagers = new ArrayList<Object>();
    
    public void reset(){
        action = "";
        purposeId = "";
        targAffId = "";
        purposeData = new HashMap<String,Object>();
        purposeAff = new ArrayList<Object>();
        purposeManagers = new ArrayList<Object>();        
    }

    public String getTargAffId() {
        return targAffId;
    }

    public void setTargAffId(String targAffId) {
        this.targAffId = targAffId;
    }

    public ArrayList<Object> getPurposeAff() {
        return purposeAff;
    }

    public void setPurposeAff(ArrayList<Object> purposeAff) {
        this.purposeAff = purposeAff;
    }

    public ArrayList<Object> getPurposeManagers() {
        return purposeManagers;
    }

    public void setPurposeManagers(ArrayList<Object> purposeManagers) {
        this.purposeManagers = purposeManagers;
    }

    public HashMap<String,Object> getPurposeData() {
        return purposeData;
    }

    public void setPurposeData(HashMap<String,Object> purposeData) {
        this.purposeData = purposeData;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
