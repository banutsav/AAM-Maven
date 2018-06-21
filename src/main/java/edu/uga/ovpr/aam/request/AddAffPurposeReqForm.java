/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import java.util.HashMap;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class AddAffPurposeReqForm extends ValidatorForm {

    private String action = "";
    private String targAffId = "";
    private String targPurpId = "";
    HashMap<String, Object> personInfo = new HashMap<String, Object>();

    public void reset() {
        action = "";
        targAffId = "";
        targPurpId = "";
        personInfo = new HashMap<String, Object>();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HashMap<String, Object> getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(HashMap<String, Object> personInfo) {
        this.personInfo = personInfo;
    }

    public String getTargAffId() {
        return targAffId;
    }

    public void setTargAffId(String targAffId) {
        this.targAffId = targAffId;
    }

    public String getTargPurpId() {
        return targPurpId;
    }

    public void setTargPurpId(String targPurpId) {
        this.targPurpId = targPurpId;
    }

}
